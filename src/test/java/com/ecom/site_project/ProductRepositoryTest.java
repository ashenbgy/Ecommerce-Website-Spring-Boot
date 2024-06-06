package com.ecom.site_project;


import com.ecom.site_project.entity.Product;
import com.ecom.site_project.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
public class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testFindByAlias() {
        String alias = "samsung";

        Product product = productRepository.findByAlias(alias);

        assertThat(product).isNotNull();
    }
}
