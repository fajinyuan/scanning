package org.eclipse.scanning.points.serialization;

import java.io.IOException;

import org.eclipse.scanning.api.points.Point;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

public class PointDeserializer extends JsonDeserializer<Point> {

	@Override
	public Point deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		PointBean bean = parser.readValueAs(PointBean.class);
		return bean.toPoint();
	}
	
	@Override
	public Object deserializeWithType(JsonParser parser, DeserializationContext context, TypeDeserializer typeDeserializer)
		throws IOException, JsonProcessingException {
		return deserialize(parser, context);
	}

}
