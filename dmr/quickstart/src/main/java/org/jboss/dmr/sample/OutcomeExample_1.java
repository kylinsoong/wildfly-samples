package org.jboss.dmr.sample;

import java.util.List;

import org.jboss.dmr.ModelNode;

public class OutcomeExample_1 {

    public static void main(String[] args) {

        final ModelNode outcome = new ModelNode();
        outcome.get("outcome").set("success");
        ModelNode resultsNode = new ModelNode();
        ModelNode row1 = new ModelNode();
        row1.get("column_1").set("value_11");
        row1.get("column_2").set("value_12");
        resultsNode.add(row1);
        ModelNode row2 = new ModelNode();
        row2.get("column_1").set("value_21");
        row2.get("column_2").set("value_22");
        resultsNode.add(row2);
        outcome.get("result").set(resultsNode);
        
        System.out.println(resultsNode);
        
        List<ModelNode> list = outcome.get("result").asList();
        System.out.println("column_1  column_2");
        for(ModelNode rowNode : list) {
            System.out.println(rowNode.get("column_1").asString() + "  " + rowNode.get("column_2").asString());
        }
    }
}
