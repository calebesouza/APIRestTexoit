package br.com.calebe.dto;

import java.io.Serializable;

public class IntervalosDTO implements Serializable {

	private static final long serialVersionUID = -7137962307504317088L;
	private IntervaloPremiosDTO min;
	private IntervaloPremiosDTO max;

	public IntervalosDTO() {
		super();
	}

	public IntervalosDTO(IntervaloPremiosDTO min, IntervaloPremiosDTO max) {
		super();
		this.min = min;
		this.max = max;
	}

	public IntervaloPremiosDTO getMin() {
		return min;
	}

	public void setMin(IntervaloPremiosDTO min) {
		this.min = min;
	}

	public IntervaloPremiosDTO getMax() {
		return max;
	}

	public void setMax(IntervaloPremiosDTO max) {
		this.max = max;
	}

}
