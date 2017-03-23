package org.eclipse.scanning.points.serialization;

import java.io.IOException;

import org.eclipse.scanning.api.points.Point;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public class PointSerializer extends JsonSerializer<Point> {

	@Override
	public void serialize(Point point, JsonGenerator gen, SerializerProvider prov)
			throws IOException, JsonProcessingException {
		try {
			final PointBean bean = new PointBean(point);
			gen.writeObject(bean);
		} catch (Throwable ne) {
			ne.printStackTrace();
			throw ne;
		}
	}
	
	@Override
	public void serializeWithType(Point point, JsonGenerator gen, SerializerProvider prov, TypeSerializer typeSer)
			throws IOException, JsonProcessingException {
		serialize(point, gen, prov);
	}

}
