package main.java.com.teamcostco.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.swing.JPanel;

/**
 * 추상 클래스 PanelController 제네릭 타입 V(뷰, JPanel을 상속) 사용
 */
public abstract class PanelController<V extends JPanel> {
	// 뷰 인스턴스, createInstance 메서드로 생성
	protected V view = createInstance(getViewClass());

	// 생성자, 뷰 인스턴스를 생성
	public PanelController() {
	}

	// 뷰 클래스 타입을 반환
	@SuppressWarnings("unchecked")
	private Class<V> getViewClass() {
		return (Class<V>) getParameterizedType(0);
	}

	// 제네릭 타입 정보를 가져옴
	private Type getParameterizedType(int index) {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			return ((ParameterizedType) type).getActualTypeArguments()[index];
		} else {
			throw new RuntimeException("Unable to determine parameterized type");
		}
	}

	// 클래스 타입을 받아 인스턴스를 생성
	private <T> T createInstance(Class<T> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(clazz.getSimpleName() + " creation failed");
		}
	}

	// 추상 메서드, 하위 클래스에서 구현 필요
	public abstract String toString();

	// 뷰 인스턴스를 반환
	public V getPanel() {
		return view;
	}
}
