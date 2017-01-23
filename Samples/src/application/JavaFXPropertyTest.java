/**
 * Copyright (c) 2016 by amphorainc.com. All rights reserved.
 * created on Dec 6, 2016
 */
package application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Saravana Kumar M
 *
 */
public class JavaFXPropertyTest
{
	private final IntegerProperty amount = new SimpleIntegerProperty();

	public final void setAmount(final Integer amount)
	{
		this.amount.set(amount);
	}

	public final Integer getAmount()
	{
		return this.amount.get();
	}

	public IntegerProperty amountProperty()
	{
		return this.amount;
	}

	private BooleanProperty isTrue = new SimpleBooleanProperty();

	public BooleanProperty isTrueProperty()
	{
		if(this.isTrue == null)
		{
			this.isTrue = new SimpleBooleanProperty();
		}
		return this.isTrue;
	}

	public static void main(final String[] args)
	{
		final JavaFXPropertyTest ob = new JavaFXPropertyTest();
		System.out.println(ob.amountProperty());
		System.out.println(ob.getAmount());
		ob.setAmount(100);
		System.out.println(ob.getAmount());

		System.out.println("=====================");

		System.out.println(ob.isTrueProperty());
	}
}