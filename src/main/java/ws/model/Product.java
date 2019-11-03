package ws.model;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import ws.helper.OntologyHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product {
    String label;
    List<Category> categories;
    double price;

    public Product(String label, List<Category> categories, double price) {
        this.label = label;
        this.categories = categories;
        this.price = price;
    }

    public static Product build(Individual ind) {
        String label = ind.getLabel(null);

        Property category = OntologyHelper.getProperty("hasCategory");
        Property price = OntologyHelper.getProperty("price");

        Resource categ = null;
        double pric = 0.0;

        if(ind.hasProperty(price)) {
            pric = ind.asResource().getProperty(price).getDouble();
        }

        List<Category> list;


        return new Product(label, null, pric);
    }
}
