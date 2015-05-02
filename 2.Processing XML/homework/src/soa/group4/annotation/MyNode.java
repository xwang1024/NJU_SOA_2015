package soa.group4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyNode {
	public boolean isSimple() default true;
	public String NS() default "";
	public String name();
	public Type type() default Type.element;
	public boolean needWrap() default true;
	public static enum Type {
		element, attribute
	}
}
