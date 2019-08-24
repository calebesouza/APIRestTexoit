package br.com.calebe.dto;

import java.io.Serializable;

public class IntervaloPremiosDTO implements Serializable, Comparable<IntervaloPremiosDTO> {

	private static final long serialVersionUID = -8195197903314034973L;

	private String producer;
	private Integer interval;
	private String previousWin;
	private String followingWin;

	public IntervaloPremiosDTO() {
		super();
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public String getPreviousWin() {
		return previousWin;
	}

	public void setPreviousWin(String previousWin) {
		this.previousWin = previousWin;
	}

	public String getFollowingWin() {
		return followingWin;
	}

	public void setFollowingWin(String followingWin) {
		this.followingWin = followingWin;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntervaloPremiosDTO other = (IntervaloPremiosDTO) obj;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
			return false;
		return true;
	}
	
	public static int compareIntervalos(IntervaloPremiosDTO a, IntervaloPremiosDTO b) {
		return a.interval.compareTo(b.interval);
	}
	
	@Override
    public String toString() {
        return "producer : " + producer + "\t" + "interval : " + interval + "\t" + "previousWin : " + previousWin + "\t" + "followingWin : " + followingWin;
    }
	
	@Override
    public int compareTo(IntervaloPremiosDTO o) {
        return getInterval().compareTo(o.getInterval());
    }

}
