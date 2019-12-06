package ws.infrastructure;

import java.io.InputStream;
import java.util.List;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.stereotype.Component;
import ws.domain.product.Product;
import ws.domain.product.ProductRepository;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public void updateProductQuantityByName(Product product) {
        OntModel ontModel = getProductOntModel();
        List<Individual> individuals = OntologyUtil.getAllIndividualsOfType("Product", ontModel);
        Property quantityProp = OntologyUtil.getProperty("quantity");

        Individual foundProduct = null;
        for (Individual individual : individuals) {
            if (product.getName().equals(individual.getPropertyValue(RDFS.label).toString())) {
                foundProduct = individual;
            }
        }

        if (foundProduct == null) {
            return;
        }

        foundProduct.removeProperty(quantityProp, foundProduct.getPropertyValue(quantityProp));
        Literal qnty = ontModel.createTypedLiteral(product.getQuantity());
        foundProduct.addProperty(quantityProp, qnty);
    }

    private OntModel getProductOntModel() {
        InputStream in = FileManager.get().open("src/main/resources/product.ttl");
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.read(in, OntologyUtil.schema, "TURTLE");

        return ontModel;
    }
}
