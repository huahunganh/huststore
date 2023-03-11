

package com.huststore.jpa.services.predicates;

import com.huststore.jpa.entities.ProductCategory;
import com.huststore.jpa.entities.QProductCategory;
import com.huststore.jpa.services.IPredicateJpaService;
import com.huststore.jpa.services.IProductCategoryTreeResolver;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class ProductCategoriesPredicateJpaServiceImpl
  implements IPredicateJpaService<ProductCategory> {

  private final Logger logger = LoggerFactory.getLogger(ProductCategoriesPredicateJpaServiceImpl.class);
  private final IProductCategoryTreeResolver treeResolver;

  @Autowired
  public ProductCategoriesPredicateJpaServiceImpl(IProductCategoryTreeResolver treeResolver) {
    this.treeResolver = treeResolver;
  }

  @Override
  public QProductCategory getBasePath() {
    return QProductCategory.productCategory;
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
          case "code":
            return getBasePath().code.eq(stringValue);
          case "name":
            predicate.and(getBasePath().name.eq(stringValue));
            break;
          case "nameLike":
            predicate.and(getBasePath().name.likeIgnoreCase("%" + stringValue + "%"));
            break;
          case "parentCode":
            if (StringUtils.isNotBlank(stringValue)) {
              predicate.and(getBasePath().parent.code.eq(stringValue));
            }
            break;
          case "parentId":
            if (StringUtils.isNotBlank(stringValue)) {
              predicate.and(getBasePath().parent.isNull());
            } else {
              predicate.and(getBasePath().parent.id.eq(Long.valueOf(stringValue)));
            }
            break;
          case "rootId":
            if (StringUtils.isNotBlank(stringValue)) {
              List<Long> branchParentIds = treeResolver.getBranchIdsFromRootId(Long.valueOf(stringValue));
              predicate.and(getBasePath().parent.id.in(branchParentIds));
            }
            break;
          case "rootCode":
            if (StringUtils.isNotBlank(stringValue)) {
              List<Long> branchParentIds = treeResolver.getBranchIdsFromRootCode(stringValue);
              if (!CollectionUtils.isEmpty(branchParentIds)) {
                predicate.and(getBasePath().parent.id.in(branchParentIds));
              }
            }
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
