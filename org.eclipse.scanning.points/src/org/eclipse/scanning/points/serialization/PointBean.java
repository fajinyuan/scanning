package org.eclipse.scanning.points.serialization;

import java.util.List;

import org.eclipse.scanning.api.points.Point;

class PointBean {
	
	private Double  x;
	private Double  y;
	private Integer xIndex;
	private Integer yIndex;
	private String  xName;
	private String  yName;
	
	public PointBean() {
		// no-arg constructor for json deserialiation
	}
	
	public PointBean(Point point) {
		setX(point.getX());
		setY(point.getY());
		List<String> names = point.getNames();
		String yName = names.get(0); // Note, y name comes first
		String xName = names.get(1);
		setxName(xName);
		setyName(yName);
		setxIndex(point.getIndex(xName));
		setyIndex(point.getIndex(yName));
	}
	
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public Integer getxIndex() {
		return xIndex;
	}
	public void setxIndex(Integer xIndex) {
		this.xIndex = xIndex;
	}
	public Integer getyIndex() {
		return yIndex;
	}
	public void setyIndex(Integer yIndex) {
		this.yIndex = yIndex;
	}
	public String getxName() {
		return xName;
	}
	public void setxName(String xName) {
		this.xName = xName;
	}
	public String getyName() {
		return yName;
	}
	public void setyName(String yName) {
		this.yName = yName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((xIndex == null) ? 0 : xIndex.hashCode());
		result = prime * result + ((xName == null) ? 0 : xName.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		result = prime * result + ((yIndex == null) ? 0 : yIndex.hashCode());
		result = prime * result + ((yName == null) ? 0 : yName.hashCode());
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
		PointBean other = (PointBean) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (xIndex == null) {
			if (other.xIndex != null)
				return false;
		} else if (!xIndex.equals(other.xIndex))
			return false;
		if (xName == null) {
			if (other.xName != null)
				return false;
		} else if (!xName.equals(other.xName))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		if (yIndex == null) {
			if (other.yIndex != null)
				return false;
		} else if (!yIndex.equals(other.yIndex))
			return false;
		if (yName == null) {
			if (other.yName != null)
				return false;
		} else if (!yName.equals(other.yName))
			return false;
		return true;
	}

	public Point toPoint() {
		return new Point(xName, xIndex, x, yName, yIndex, y);
	}
	
}
