package com.acme.corp.tracker.client;

import java.util.List;
import java.util.Map;

import org.jboss.as.console.client.Console;
import org.jboss.as.console.client.layout.MultipleToOneLayout;
import org.jboss.as.console.client.rbac.SecurityFramework;
import org.jboss.as.console.client.v3.ResourceDescriptionRegistry;
import org.jboss.as.console.client.v3.dmr.AddressTemplate;
import org.jboss.as.console.client.v3.dmr.ResourceDescription;
import org.jboss.as.console.mbui.widgets.ModelNodeFormBuilder;
import org.jboss.ballroom.client.rbac.SecurityContext;
import org.jboss.ballroom.client.widgets.forms.FormCallback;
import org.jboss.ballroom.client.widgets.tables.DefaultCellTable;
import org.jboss.ballroom.client.widgets.tools.ToolButton;
import org.jboss.ballroom.client.widgets.tools.ToolStrip;
import org.jboss.ballroom.client.widgets.window.Feedback;
import org.jboss.dmr.client.Property;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class TypeView {

    private static final AddressTemplate ADDRESS = AddressTemplate.of("{selected.profile}/subsystem=tracker/type=*");
    
    private final ExtensionPresenter presenter;

    private final DefaultCellTable table;
    
    private SecurityFramework securityFramework;
    private ResourceDescriptionRegistry descriptionRegistry;
    
    private final ListDataProvider<Property> dataProvider;
    private SingleSelectionModel<Property> selectionModel;
    
    public TypeView(ExtensionPresenter presenter, SecurityFramework securityFramework, ResourceDescriptionRegistry descriptionRegistry) {
        super();
        this.presenter = presenter;
        this.securityFramework = securityFramework; 
        this.descriptionRegistry = descriptionRegistry;
        ProvidesKey<Property> providesKey = Property::getName;
        this.table = new DefaultCellTable<>(5, providesKey);
        this.dataProvider = new ListDataProvider<>(providesKey);
        this.dataProvider.addDataDisplay(table);
    }
    
    public Widget asWidget() {
        
        TextColumn<Property> nameColumn = new TextColumn<Property>() {
            @Override
            public String getValue(Property node) {
                return node.getName();
            }
        };

        TextColumn<Property> enabledColumn = new TextColumn<Property>() {
            @Override
            public String getValue(Property node) {
                return String.valueOf(node.getValue().get("tick").asString());
            }
        };

        table.addColumn(nameColumn, "Name");
        table.addColumn(enabledColumn, "Tick");
        
        ToolStrip tools = new ToolStrip();
        tools.addToolButtonRight(new ToolButton(Console.CONSTANTS.common_label_add(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.onLaunchAddResourceDialog(ADDRESS);
            }
        }));
        tools.addToolButtonRight(new ToolButton(Console.CONSTANTS.common_label_delete(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Feedback.confirm(Console.MESSAGES.deleteTitle("Tracker Type"),
                        Console.MESSAGES.deleteConfirm("Tracker Type '" + getCurrentSelection().getName() + "'"),
                        new Feedback.ConfirmationHandler() {
                            @Override
                            public void onConfirmation(boolean isConfirmed) {
                                if (isConfirmed) {
                                    presenter.onRemoveResource(ADDRESS, getCurrentSelection().getName());
                                }
                            }
                        });
            }
        }));
        
        SecurityContext securityContext = securityFramework.getSecurityContext(presenter.getProxy().getNameToken());
        final ResourceDescription definition = descriptionRegistry.lookup(ADDRESS);

        final ModelNodeFormBuilder.FormAssets formAssets = new ModelNodeFormBuilder()
                    .setConfigOnly()
                    .setResourceDescription(definition)
                    .setSecurityContext(securityContext).build();
        
        formAssets.getForm().setToolsCallback(new FormCallback() {
            @Override
            public void onSave(Map changeset) {
                presenter.onSaveNamedResource(ADDRESS, getCurrentSelection().getName(), changeset);
            }

            @Override
            public void onCancel(Object entity) {
                formAssets.getForm().cancel();
            }
        });
        
        VerticalPanel formPanel = new VerticalPanel();
        formPanel.setStyleName("fill-layout-width");
        formPanel.add(formAssets.getHelp().asWidget());
        formPanel.add(formAssets.getForm().asWidget());
        
        MultipleToOneLayout layoutBuilder = new MultipleToOneLayout()
                .setPlain(true)
                .setHeadline("Types")
                .setMasterTools(tools)
                .setMaster(Console.MESSAGES.available("Types"), table)
                .addDetail(Console.CONSTANTS.common_label_attributes(), formPanel);

        
        selectionModel = new SingleSelectionModel<Property>();
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                Property server = selectionModel.getSelectedObject();
                if(server!=null)
                {
                    formAssets.getForm().edit(server.getValue());
                }
                else
                {
                    formAssets.getForm().clearValues();
                }
            }
        });
        table.setSelectionModel(selectionModel);
        return layoutBuilder.build();
    }
    
    private Property getCurrentSelection() {
        Property selection = ((SingleSelectionModel<Property>) table.getSelectionModel()).getSelectedObject();
        return selection;
    }
    
    public void setData(List<Property> data) {
        dataProvider.setList(data);
        table.selectDefaultEntity();
        SelectionChangeEvent.fire(selectionModel); // updates ModelNodeForm's editedEntity with current value
    }

}
