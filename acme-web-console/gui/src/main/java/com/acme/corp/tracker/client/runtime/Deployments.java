package com.acme.corp.tracker.client.runtime;

import java.util.List;

import org.jboss.as.console.client.layout.SimpleLayout;
import org.jboss.ballroom.client.widgets.tables.DefaultCellTable;
import org.jboss.ballroom.client.widgets.tables.DefaultPager;
import org.jboss.ballroom.client.widgets.tools.ToolButton;
import org.jboss.ballroom.client.widgets.tools.ToolStrip;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class Deployments {
    
    private DeploymentPresenter presenter;
    private boolean isCool;
    
    private DefaultCellTable<String> table;
    private ListDataProvider<String> dataProvider;
    
    public Deployments(DeploymentPresenter presenter, boolean isCool) {
        this.presenter = presenter;
        this.isCool = isCool;
    }

    @SuppressWarnings("unchecked")
    Widget asWidget(){
        
        table = new DefaultCellTable<String>(5, new ProvidesKey<String>(){

            @Override
            public Object getKey(String item) {
                return item;
            }});
        
        table.setSelectionModel(new SingleSelectionModel<String>());
        
        dataProvider = new ListDataProvider<String>();
        dataProvider.addDataDisplay(table);
        
        com.google.gwt.user.cellview.client.Column<String, String> nameColumn = new com.google.gwt.user.cellview.client.Column<String, String>(new TextCell()){

            @Override
            public String getValue(String object) {
                return object;
            }};
            
        if(isCool){
            table.addColumn(nameColumn, "Cool Deployments");
        } else {
            table.addColumn(nameColumn, "Deployments");
        }
        
        table.getSelectionModel().addSelectionChangeHandler(new SelectionChangeEvent.Handler(){

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                String deploy = ((SingleSelectionModel<String>) table.getSelectionModel()).getSelectedObject();
                // do nothing
            }});
        
        ToolStrip tools = new ToolStrip();
        final ToolButton refreshBtn = new ToolButton("Refresh", new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                presenter.reloadDeployments(isCool);
            }});
        refreshBtn.setVisible(true);
        tools.addToolButtonRight(refreshBtn);
        tools.setVisible(true);
        
        DefaultPager pager = new DefaultPager();
        pager.setDisplay(table);

        VerticalPanel tablePanel = new VerticalPanel();
        tablePanel.setStyleName("fill-layout-width");
        tablePanel.add(table);
        tablePanel.add(pager);
        
        HTML refreshlink = new HTML("<i class='icon-refresh'></i> Refresh");
        refreshlink.setStyleName("html-link");
        refreshlink.getElement().getStyle().setMarginTop(30, Style.Unit.PX);
        refreshlink.getElement().getStyle().setMarginBottom(-20,Style.Unit.PX);
        refreshlink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.reloadDeployments(isCool);
            }
        });
        refreshlink.getElement().getParentElement().getStyle().setTextAlign(Style.TextAlign.RIGHT);
        
        SimpleLayout layout = new SimpleLayout()
                .setPlain(true)
                .setTitle(isCool ? "Cool Deployments" : "Deployments")
                .setHeadline(isCool ? "Cool Deployments" : "Deployments")
                .addContent("", tools)
                .addContent("Deployments", tablePanel)
                .addContent("", refreshlink);

        return layout.build();
    }
    
    public void setDeployments(List<String> deployments) {
        this.dataProvider.setList(deployments);
        this.table.selectDefaultEntity();
    }

}
