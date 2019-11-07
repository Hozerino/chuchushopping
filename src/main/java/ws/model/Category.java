package ws.model;

import org.apache.jena.ontology.Individual;

public class Category {
    private String label;

    public Category(String label) {
        this.label = label;
    }

    public static Category build(Individual ind) {
        String label = ind.getLabel(null);
        return new Category(label);
    }
}
