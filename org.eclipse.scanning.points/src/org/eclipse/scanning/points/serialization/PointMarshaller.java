package org.eclipse.scanning.points.serialization;

import org.eclipse.dawnsci.analysis.api.persistence.IMarshaller;
import org.eclipse.scanning.api.points.Point;

public class PointMarshaller implements IMarshaller {

	@Override
	public Class<Point> getObjectClass() {
		return Point.class;
	}

	@Override
	public Class<PointSerializer> getSerializerClass() {
		return PointSerializer.class;
	}

	@Override
	public Class<PointDeserializer> getDeserializerClass() {
		return PointDeserializer.class;
	}

}
