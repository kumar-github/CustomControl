package application;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateTest
{
	public static void main(final String[] args)
	{
		filter(null, null);
	}

	//private final Predicate<T> isNullPredicate = Objects::isNull;
	private static <T> List<T> filter(final List<T> listToFilter, final Predicate<T> conditionToFilter)
	{
		final Predicate<T> isNullPredicate = Objects::isNull;
		conditionToFilter.and(isNullPredicate);
		System.out.println("before filter : " + listToFilter.size());
		//return listToFilter.stream().filter(conditionToFilter).collect(Collectors.<T>toList());
		final List<T> filteredList = listToFilter.stream().filter(conditionToFilter).collect(Collectors.<T>toList());
		System.out.println("after filter : " + listToFilter.size());
		return filteredList;
	}
}