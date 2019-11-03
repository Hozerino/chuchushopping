package ws.service;

import org.apache.jena.ontology.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NavigationService {

    //		Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
//		model.read(owl.getAbsolutePath(), TURTLE_LANGUAGE);
//
//		Individual brennao = ((OntModel) model).getIndividual(schema + "Brenno_Cremonini");
//		Individual space = ((OntModel) model).getIndividual(schema + "Corredor1-1");
//
//		Property p = new PropertyImpl(schema + "bottomOf");
//		space.getProperty(p);
    List<Individual> ground;

    @Autowired
    OntologyService ontologyService;

    @PostConstruct
    private void postConstruct() {
        List<Individual> ground = ontologyService.getAllIndividualsOfType("Space");
        // montar a matrix vai ser pica, se eh q vai ser feito assim
    }

    // vai receber a URI de uma loja e vai achar o caminho mais curto ate qualquer chao daquela loja
    public List<Individual> pathTo(Individual store) {
        // soh tem um, se n achar eh pq o mapa ta um lixo
        Individual startingPoint = ontologyService.getAllIndividualsOfType("CommercialCenter").get(0);
        // TODO vai que eh tua, cabecao
        return null;
    }
}
