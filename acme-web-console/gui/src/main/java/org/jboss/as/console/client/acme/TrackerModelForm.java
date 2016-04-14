package org.jboss.as.console.client.acme;

import static com.google.gwt.dom.client.Style.Unit.PX;

import java.util.List;
import java.util.Map;

import org.jboss.as.console.client.shared.help.FormHelpPanel;
import org.jboss.as.console.client.shared.subsys.Baseadress;
import org.jboss.as.console.client.widgets.forms.FormToolStrip;
import org.jboss.ballroom.client.widgets.forms.Form;
import org.jboss.ballroom.client.widgets.forms.FormItem;
import org.jboss.ballroom.client.widgets.forms.FormValidation;
import org.jboss.ballroom.client.widgets.forms.FormValidator;
import org.jboss.ballroom.client.widgets.tables.DefaultCellTable;
import org.jboss.dmr.client.ModelNode;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TrackerModelForm<T> {

    private Form<T> form;
    private Class<T> type;
    private FormItem<?>[] fields;
    private Label formValidationError;
    private Persistable<T> presenter;
    private DefaultCellTable<T> table;
    
    public TrackerModelForm(Class<T> type, Persistable<T> presenter, FormItem<?>... fields) {
        this.type = type;
        this.presenter = presenter;
        this.fields = fields;
    }
    
    Widget asWidget() {
        VerticalPanel layout = new VerticalPanel();
        layout.setStyleName("fill-layout");

        this.form = new Form<T>(type);
        this.form.setNumColumns(2);
        
        this.form.addFormValidator(new FormValidator() {
            @Override
            public void validate(List<FormItem> formItems, FormValidation outcome) {
                validateForm(outcome);
            }
        });
        
        FormToolStrip<T> attributesToolStrip = new FormToolStrip<T>(this.form,
                new FormToolStrip.FormCallback<T>() {
                    @Override
                    public void onSave(Map<String, Object> changeset) {
                        presenter.save(form.getEditedEntity(),form.getChangedValues());
                    }

                    @Override
                    public void onDelete(T entity) {
                        // this is not delete, it is Cancel
                    }
                });
        layout.add(attributesToolStrip.asWidget());
        
        FormHelpPanel helpPanel = new FormHelpPanel(new FormHelpPanel.AddressCallback() {
            @Override
            public ModelNode getAddress() {
                ModelNode address = Baseadress.get();
                address.add("subsystem", "tracker");
                return address;
            }
        }, this.form);
        
        layout.add(helpPanel.asWidget());

        this.formValidationError = new Label("Form is invalid!");
        this.formValidationError.addStyleName("form-error-desc");
        this.formValidationError.getElement().getStyle().setLineHeight(9, PX);
        this.formValidationError.getElement().getStyle().setMarginBottom(5, PX);
        this.formValidationError.setVisible(false);
        layout.add(formValidationError.asWidget());

        if (this.table != null) {
            this.form.bind(this.table);
        }
        
        this.form.setFields(this.fields);
        this.form.setEnabled(false);

        layout.add(this.form.asWidget());

        return layout;
    }
    
    public void edit(T t) {
        form.edit(t);
    }

    public void clearValues() {
        form.clearValues();
    }

    protected FormValidation validateForm(FormValidation formValidation) {
        return formValidation;
    }
    
    public void setTable(DefaultCellTable<T> table) {
        this.table = table;
    }
}
