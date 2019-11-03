package ws.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import ws.helper.OntologyHelper;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Store {
    String label;
    String telephone;
    String website;
    List<Product> sells = new ArrayList<>();

    public Store(String label, String telephone, String website) {
        this.label = label;
        this.telephone = telephone;
        this.website = website;
    }

    public void addProduct(Product newProduct) {
        sells.add(newProduct);
    }

    public static Store build(Individual ind) {
        String label = ind.getLabel(null);

        Property telephone = OntologyHelper.getProperty("telephone");
        Property website = OntologyHelper.getProperty("website");

        String telephoneString = "N/A";
        String websiteString = "N/A";
        if(ind.hasProperty(telephone)) {
            telephoneString = ind.asResource().getProperty(telephone).getString();
        }
        if(ind.hasProperty(website)) {
            websiteString = ind.asResource().getProperty(website).getString();
        }

        return new Store(label, telephoneString, websiteString);
    }
}
