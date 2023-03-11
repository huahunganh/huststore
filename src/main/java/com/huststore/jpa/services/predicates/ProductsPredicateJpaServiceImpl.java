

package com.huststore.jpa.services.predicates;

import com.huststore.jpa.entities.Product;
import com.huststore.jpa.entities.QProduct;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.IProductCategoryTreeResolver;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductsPredicateJpaServiceImpl
  implements IPredicateJpaService<Product> {

  private final Logger logger = LoggerFactory.getLogger(ProductsPredicateJpaServiceImpl.class);
  private final IProductCategoryTreeResolver categoryTreeResolver;

  @Autowired
  public ProductsPredicateJpaServiceImpl(IProductCategoryTreeResolver categoryTreeResolver) {
    this.categoryTreeResolver = categoryTreeResolver;
  }

  @Override
  public QProduct getBasePath() {
    return QProduct.product;
  }

  @Override
  public Predicate parseMap(Map<String, String> queryParamsMap) {
    BooleanBuilder predicate = new BooleanBuilder();
    for (Map.Entry<String, String> entry : queryParamsMap.entrySet()) {
      String paramName = entry.getKey();
      String stringValue = entry.getValue();
      try {
        switch (paramName) {
          case "id":
            return getBasePath().id.eq(Long.valueOf(stringValue));
          case "barcode":
            return getBasePath().barcode.eq(stringValue);
          case "name":
            return getBasePath().name.eq(stringValue);
          case "barcodeLike":
            predicate.and(getBasePath().barcode.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "nameLike":
            predicate.and(getBasePath().name.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "categoryCode":
            List<Long> branchIds = categoryTreeResolver.getBranchIdsFromRootCode(stringValue);
            predicate.and(getBasePath().productCategory.code.eq(stringValue)
                              .or(getBasePath().productCategory.id.in(branchIds)));
            break;
          case "categoryCodeLike":
            predicate.and(getBasePath().productCategory.code.likeIgnoreCase("%" + stringValue + "%"));
            break;
          default:
            break;
        }
      } catch (NumberFormatException exc) {
        logger.info("Param '{}' couldn't be parsed as number (value: '{}')", paramName, stringValue);
      }
    }

    return predicate;
  }
}
