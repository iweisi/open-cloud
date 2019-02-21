package com.github.lyd.common.mapper;

import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;

/**
 * 动态条件构造器
 * 支持构建一组或多组条件
 * (a=1 and b=2) and (c=1 and d=2)
 * 适用于tkmapper 通用mapper
 *
 * @author admin
 */
public class ExampleBuilder {
    /**
     * 定义examle对象，用于返回
     */
    private Example example;

    private Class<?> aClass;

    private CriteriaBuilder criteriaBuilder;

    public ExampleBuilder(Class<?> tClass) {
        this.aClass = tClass;
        this.example = new Example(tClass);
        this.criteriaBuilder = new CriteriaBuilder(this);
    }//构造方法中传入Class参数，实例化example

    /**
     * 创建创建创建查询条件
     *
     * @return
     */
    public CriteriaBuilder criteria() {
        criteriaBuilder.newCriteria();
        return criteriaBuilder;
    }

    /**
     * 创建多组条件比如(a=1 and b=2) and (c=1 and d=2)
     *
     * @return
     */
    public CriteriaBuilder and() {
        example.and(criteriaBuilder.newCriteria());
        return criteriaBuilder;
    }

    /**
     * 创建多组条件比如(a=1 and b=2) or (c=1 and d=2)
     *
     * @return
     */
    public CriteriaBuilder or() {
        example.or(criteriaBuilder.newCriteria());
        return criteriaBuilder;
    }

    public static class CriteriaBuilder {
        private Example.Criteria criteria;
        private ExampleBuilder exampleBuilder;

        public CriteriaBuilder(ExampleBuilder exampleBuilder) {
            this.exampleBuilder = exampleBuilder;
        }

        private Example.Criteria newCriteria() {
            criteria = this.exampleBuilder.example.createCriteria();
            return criteria;
        }

        public ExampleBuilder end() {
            return exampleBuilder;
        }

        /*andLike, andLikeLeft, andLikeRight, andEqualTo, andNotEqualTo, andGreaterThan, andGreaterThanOrEqualTo, andLessThan, andLessThanOrEqualTo, andIsNull, andIsNotNull, andIn, andBetween, andNotBetween, andNotIn,
                orLike, orLikeLeft, orLikeRight, orEqualTo, orNotEqualTo, orGreaterThan, orGreaterThanOrEqualTo, orLessThan, orLessThanOrEqualTo, orIsNull, orIsNotNull, orIn, orBetween, orNotBetween, orNotIn*/
        public CriteriaBuilder andLike(String property, Object value) {
            andCriteria(criteria, Expression.andLike, property, value);
            return this;
        }

        public CriteriaBuilder andLikeLeft(String property, Object value) {
            andCriteria(criteria, Expression.andLikeLeft, property, value);
            return this;
        }

        public CriteriaBuilder andLikeRight(String property, Object value) {
            andCriteria(criteria, Expression.andLikeRight, property, value);
            return this;
        }

        public CriteriaBuilder andEqualTo(String property, Object value) {
            andCriteria(criteria, Expression.andEqualTo, property, value);
            return this;
        }

        public CriteriaBuilder andNotEqualTo(String property, Object value) {
            andCriteria(criteria, Expression.andNotEqualTo, property, value);
            return this;
        }

        public CriteriaBuilder andGreaterThan(String property, Object value) {
            andCriteria(criteria, Expression.andGreaterThan, property, value);
            return this;
        }

        public CriteriaBuilder andGreaterThanOrEqualTo(String property, Object value) {
            andCriteria(criteria, Expression.andGreaterThanOrEqualTo, property, value);
            return this;
        }

        public CriteriaBuilder andLessThan(String property, Object value) {
            andCriteria(criteria, Expression.andLessThan, property, value);
            return this;
        }

        public CriteriaBuilder andLessThanOrEqualTo(String property, Object value) {
            andCriteria(criteria, Expression.andLessThanOrEqualTo, property, value);
            return this;
        }

        public CriteriaBuilder andIsNull(String property, Object value) {
            andCriteria(criteria, Expression.andIsNull, property, value);
            return this;
        }

        public CriteriaBuilder andIsNotNull(String property, Object value) {
            andCriteria(criteria, Expression.andIsNotNull, property, value);
            return this;
        }

        public CriteriaBuilder andIn(String property, Object value) {
            andCriteria(criteria, Expression.andIn, property, value);
            return this;
        }

        public CriteriaBuilder andBetween(String property, Object value) {
            andCriteria(criteria, Expression.andBetween, property, value);
            return this;
        }

        public CriteriaBuilder andNotBetween(String property, Object value) {
            andCriteria(criteria, Expression.andNotBetween, property, value);
            return this;
        }

        public CriteriaBuilder andNotIn(String property, Object value) {
            andCriteria(criteria, Expression.andNotIn, property, value);
            return this;
        }


        public CriteriaBuilder orLike(String property, Object value) {
            orCriteria(criteria, Expression.orLike, property, value);
            return this;
        }

        public CriteriaBuilder orLikeLeft(String property, Object value) {
            orCriteria(criteria, Expression.orLikeLeft, property, value);
            return this;
        }

        public CriteriaBuilder orLikeRight(String property, Object value) {
            orCriteria(criteria, Expression.orLikeRight, property, value);
            return this;
        }

        public CriteriaBuilder orEqualTo(String property, Object value) {
            orCriteria(criteria, Expression.orEqualTo, property, value);
            return this;
        }

        public CriteriaBuilder orNotEqualTo(String property, Object value) {
            orCriteria(criteria, Expression.orNotEqualTo, property, value);
            return this;
        }

        public CriteriaBuilder orGreaterThan(String property, Object value) {
            orCriteria(criteria, Expression.orGreaterThan, property, value);
            return this;
        }

        public CriteriaBuilder orGreaterThanOrEqualTo(String property, Object value) {
            orCriteria(criteria, Expression.orGreaterThanOrEqualTo, property, value);
            return this;
        }

        public CriteriaBuilder orLessThan(String property, Object value) {
            orCriteria(criteria, Expression.orLessThan, property, value);
            return this;
        }

        public CriteriaBuilder orLessThanOrEqualTo(String property, Object value) {
            orCriteria(criteria, Expression.orLessThanOrEqualTo, property, value);
            return this;
        }

        public CriteriaBuilder orIsNull(String property, Object value) {
            orCriteria(criteria, Expression.orIsNull, property, value);
            return this;
        }

        public CriteriaBuilder orIsNotNull(String property, Object value) {
            orCriteria(criteria, Expression.orIsNotNull, property, value);
            return this;
        }

        public CriteriaBuilder orIn(String property, Object value) {
            orCriteria(criteria, Expression.orIn, property, value);
            return this;
        }

        public CriteriaBuilder orBetween(String property, Object value) {
            orCriteria(criteria, Expression.orBetween, property, value);
            return this;
        }

        public CriteriaBuilder orNotBetween(String property, Object value) {
            orCriteria(criteria, Expression.orNotBetween, property, value);
            return this;
        }

        public CriteriaBuilder orNotIn(String property, Object value) {
            orCriteria(criteria, Expression.orNotIn, property, value);
            return this;
        }

    }


    /**
     * 构建and条件
     *
     * @param criteria
     * @param property
     * @param expression
     * @param value
     */
    private static  void andCriteria(Example.Criteria criteria, Expression expression, String property, Object value) {  //组装参数
        switch (expression) {
            case andLike:
                if (!StringUtils.isEmpty(value)) {
                    criteria.andLike(property, "%" + value + "%");
                }
                break;
            case andLikeLeft:
                if (!StringUtils.isEmpty(value)) {
                    criteria.andLike(property, "%" + value);
                }
                break;
            case andLikeRight:
                if (!StringUtils.isEmpty(value)) {
                    criteria.andLike(property, value + "%");
                }
                break;
            case andEqualTo:
                if (!StringUtils.isEmpty(value)) {
                    criteria.andEqualTo(property, value);
                }
                break;
            case andNotEqualTo:
                if (!StringUtils.isEmpty(value)) {
                    criteria.andNotEqualTo(property, value);
                }
                break;
            case andGreaterThan:
                if (!StringUtils.isEmpty(value)) {
                    criteria.andGreaterThan(property, value);
                }
                break;
            case andGreaterThanOrEqualTo:
                if (!StringUtils.isEmpty(value)) {
                    criteria.andGreaterThanOrEqualTo(property, value);
                }
                break;
            case andLessThan:
                if (!StringUtils.isEmpty(value)) {
                    criteria.andLessThan(property, value);
                }
                break;
            case andLessThanOrEqualTo:
                if (!StringUtils.isEmpty(value)) {
                    criteria.andLessThanOrEqualTo(property, value);
                }
                break;
            case andIsNull:
                criteria.andIsNull(property);
                break;
            case andIsNotNull:
                criteria.andIsNotNull(property);
                break;
            case andIn:
                if (!StringUtils.isEmpty(value)) {
                    if (value instanceof ArrayList) {
                        criteria.andIn(property, (ArrayList) value);
                    }
                }
                break;
            case andBetween:
                if (!StringUtils.isEmpty(value)) {
                    if (value instanceof ArrayList) {
                        criteria.andBetween(property, ((ArrayList) value).get(0),
                                ((ArrayList) value).get(1));
                    }
                }
                break;
            case andNotBetween:
                if (!StringUtils.isEmpty(value)) {
                    if (value instanceof ArrayList) {
                        criteria.andNotBetween(property, ((ArrayList) value).get(0),
                                ((ArrayList) value).get(1));
                    }
                }
                break;
            case andNotIn:
                if (!StringUtils.isEmpty(value)) {
                    if (value instanceof ArrayList) {
                        criteria.andNotIn(property, (ArrayList) value);
                    }
                }
                break;
            default:
        }
    }


    private static  void orCriteria(Example.Criteria criteria, Expression expression, String property, Object value) {  //组装参数
        switch (expression) {
            case orLike:
                if (!StringUtils.isEmpty(value)) {
                    criteria.orLike(property, "%" + value + "%");
                }
                break;
            case orLikeLeft:
                if (!StringUtils.isEmpty(value)) {
                    criteria.orLike(property, "%" + value);
                }
                break;
            case orLikeRight:
                if (!StringUtils.isEmpty(value)) {
                    criteria.orLike(property, value + "%");
                }
                break;
            case orEqualTo:
                if (!StringUtils.isEmpty(value)) {
                    criteria.orEqualTo(property, value);
                }
                break;
            case orNotEqualTo:
                if (!StringUtils.isEmpty(value)) {
                    criteria.orNotEqualTo(property, value);
                }
                break;
            case orGreaterThan:
                if (!StringUtils.isEmpty(value)) {
                    criteria.orGreaterThan(property, value);
                }
                break;
            case orGreaterThanOrEqualTo:
                if (!StringUtils.isEmpty(value)) {
                    criteria.orGreaterThanOrEqualTo(property, value);
                }
                break;
            case orLessThan:
                if (!StringUtils.isEmpty(value)) {
                    criteria.orLessThan(property, value);
                }
                break;
            case orLessThanOrEqualTo:
                if (!StringUtils.isEmpty(value)) {
                    criteria.orLessThanOrEqualTo(property, value);
                }
                break;
            case orIsNull:
                criteria.orIsNull(property);
                break;
            case orIsNotNull:
                criteria.orIsNotNull(property);
                break;
            case orIn:
                if (!StringUtils.isEmpty(value)) {
                    if (value instanceof ArrayList) {
                        criteria.orIn(property, (ArrayList) value);
                    }
                }
                break;
            case orBetween:
                if (!StringUtils.isEmpty(value)) {
                    if (value instanceof ArrayList) {
                        criteria.orBetween(property, ((ArrayList) value).get(0),
                                ((ArrayList) value).get(1));
                    }
                }
                break;
            case orNotBetween:
                if (!StringUtils.isEmpty(value)) {
                    if (value instanceof ArrayList) {
                        criteria.orNotBetween(property, ((ArrayList) value).get(0),
                                ((ArrayList) value).get(1));
                    }
                }
                break;
            case orNotIn:
                if (!StringUtils.isEmpty(value)) {
                    if (value instanceof ArrayList) {
                        criteria.orNotIn(property, (ArrayList) value);
                    }
                }
                break;
            default:
        }
    }

    public Example build() {  //返回example实例
        return example;
    }

    public enum Expression {
        andLike, andLikeLeft, andLikeRight, andEqualTo, andNotEqualTo, andGreaterThan, andGreaterThanOrEqualTo, andLessThan, andLessThanOrEqualTo, andIsNull, andIsNotNull, andIn, andBetween, andNotBetween, andNotIn,
        orLike, orLikeLeft, orLikeRight, orEqualTo, orNotEqualTo, orGreaterThan, orGreaterThanOrEqualTo, orLessThan, orLessThanOrEqualTo, orIsNull, orIsNotNull, orIn, orBetween, orNotBetween, orNotIn
    }


}
