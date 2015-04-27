package soa.group4.annotation;

public @interface MyList {
	public String name();
	public String itemName();
	public boolean isItemSimple() default true;
}
