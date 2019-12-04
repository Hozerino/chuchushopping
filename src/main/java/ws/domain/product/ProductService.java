package ws.domain.product;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private void updateProductQuantity(Product product) {
        productRepository.updateProductQuantityByName(product);
    }
}
