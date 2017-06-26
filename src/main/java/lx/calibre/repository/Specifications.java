package lx.calibre.repository;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.criterion.MatchMode;
import org.springframework.data.jpa.domain.Specification;

/**
 * 常用的查询条件。
 */
public class Specifications {

	/**
	 * 字段等于
	 * 
	 * @param attribute 实体中的属性
	 * @param value 待查询的值
	 * @return
	 */
	public static <X, T> Specification<X> equal(final SingularAttribute<? super X, T> attribute, final T value) {
		return new Specification<X>() {

			@Override
			public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(attribute), value);
			}

		};
	}

	/**
	 * 字段为空
	 * 
	 * @param attribute 实体中的属性
	 * @return
	 */
	public static <X, T> Specification<X> isNull(final SingularAttribute<? super X, T> attribute) {
		return new Specification<X>() {

			@Override
			public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isNull(root.get(attribute));
			}

		};
	}

	/**
	 * 字段非空
	 * 
	 * @param attribute 实体中的属性
	 * @return
	 */
	public static <X, T> Specification<X> isNotNull(final SingularAttribute<? super X, T> attribute) {
		return new Specification<X>() {

			@Override
			public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isNotNull(root.get(attribute));
			}

		};
	}

	/**
	 * 字段为<code>true</code>
	 * 
	 * @param attribute 实体中的属性
	 * @return
	 */
	public static <X> Specification<X> isTrue(final SingularAttribute<? super X, Boolean> attribute) {
		return new Specification<X>() {

			@Override
			public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isTrue(root.get(attribute));
			}

		};
	}

	/**
	 * 字段为<code>false</code>
	 * 
	 * @param attribute 实体中的属性
	 * @return
	 */
	public static <X> Specification<X> isFalse(final SingularAttribute<? super X, Boolean> attribute) {
		return new Specification<X>() {

			@Override
			public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isFalse(root.get(attribute));
			}

		};
	}

	/**
	 * 字段内容匹配，默认使用包含匹配策略 <code>('%' + pattern + '%')</code>
	 * 
	 * @param attribute 实体中的属性
	 * @param pattern 匹配模式
	 * @return
	 */
	public static <X> Specification<X> like(SingularAttribute<? super X, String> attribute, String pattern) {
		return like(attribute, pattern, MatchMode.ANYWHERE);
	}

	/**
	 * 字段内容匹配
	 * 
	 * @param attribute 实体中的属性
	 * @param pattern 匹配模式
	 * @param mode 匹配策略 {@link MatchMode}
	 * @return
	 */
	public static <X> Specification<X> like(final SingularAttribute<? super X, String> attribute, final String pattern, final MatchMode mode) {
		return new Specification<X>() {

			@Override
			public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get(attribute), mode.toMatchString(pattern));
			}

		};
	}

	/**
	 * 字段内容匹配，默认使用包含匹配策略 <code>('%' + pattern + '%')</code>
	 * 
	 * @param attribute 实体中的属性
	 * @param patterns 一组匹配模式，匹配其中任意一项
	 * @return
	 */
	public static <X> Specification<X> like(SingularAttribute<? super X, String> attribute, Collection<String> patterns) {
		return like(attribute, patterns, MatchMode.ANYWHERE);
	}

	/**
	 * 字段内容匹配
	 * 
	 * @param attribute 实体中的属性
	 * @param patterns 一组匹配模式，匹配其中任意一项
	 * @param mode 匹配策略 {@link MatchMode}
	 * @return
	 */
	public static <X> Specification<X> like(final SingularAttribute<? super X, String> attribute, final Collection<String> patterns,
			final MatchMode mode) {
		return new Specification<X>() {

			@Override
			public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Expression<String> exp = root.get(attribute);
				Predicate[] predicates = new Predicate[patterns.size()];
				int i = 0;
				for (String s : patterns) {
					predicates[i++] = cb.like(exp, mode.toMatchString(s));
				}
				return cb.or(predicates);
			}

		};
	}

}
