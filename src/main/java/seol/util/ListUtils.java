package seol.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtils {

	public static <T, ID> List<T> distinct(List<T> targetList, Function<T, ID> getIdFunction) {
		return targetList.stream()
				.filter(distinctFilter(getIdFunction))
				.collect(Collectors.toList());
	}

	public static <T, ID> Predicate<T> distinctFilter(Function<T, ID> getIdFunction) {
		Map<ID, Boolean> distinctChecker = new ConcurrentHashMap<>();
		return obj -> distinctChecker.putIfAbsent(getIdFunction.apply(obj), true) == null;
	}

	public static <T, ID> boolean isExistByKey(ID findId, List<T> targetList, Function<T, ID> getIdFunction) {
		return targetList.stream()
				.anyMatch(object -> findId.equals(getIdFunction.apply(object)));
	}

	public static <T> List<T> filter(Predicate<T> predi, List<T> list) {
		if(list==null) return null;
		List<T> result = new ArrayList<T>();
		list.forEach(value->{
			if(predi.test(value)) result.add(value);
		});
		return result;
	}

	public static <T, R> List<R> map(Function<T, R> f, List<T> list) {
		if(list==null) return null;
		List<R> result = new ArrayList<R>();
		list.forEach(value->{
			result.add(f.apply(value));
		});
		return result;
	}

	public static <T> T reduce(BiFunction<T, T, T> f, T initValue, List<T> list){
		if(list==null || list.size()==0) return initValue;
		for(T value : list) {
			initValue = f.apply(initValue, value);
		}
		return initValue;
	}

	public static <T> T reduce(BiFunction<T, T, T> f, List<T> list){
		if(list==null || list.size()==0) return null;
		T initValue = list.get(0);
		list = list.subList(1, list.size());
		return reduce(f, initValue, list);
	}

	public <T1, T2, R> Function<T1, Function<T2, R>> curry(BiFunction<T1, T2, R> f) {
		return (T1 a) -> (T2 b) -> f.apply(a, b);
	}

	private ListUtils() {
	}

}
