package com.tistory.bryage;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GeometryTest {

    /*
    @See http://esri.github.io/geometry-api-java/doc/Polygon.html
    */
    @Test
    public void createPolygon() {

        // given
        GeometryFactory geometryFactory = new GeometryFactory(CoordinateArraySequenceFactory.instance());
        List<Coordinate> coordinates = Lists.newArrayList(
                new Coordinate(34.877737, 128.6917877),
                new Coordinate(34.8721037, 128.6816597),
                new Coordinate(34.8530887, 128.6845779),
                new Coordinate(34.853793, 128.7118721),
                new Coordinate(34.877737, 128.6917877));

        CoordinateArraySequence coordinateArraySequence = new CoordinateArraySequence(coordinates.toArray(new Coordinate[] {}));
        LinearRing outerRing = new LinearRing(coordinateArraySequence, geometryFactory);

        // when
        Polygon polygon = new Polygon(outerRing, null, geometryFactory); // shell : outer boundary, holes : inner boundary
//        Polygon polygon = geometryFactory.createPolygon(coordinateArraySequence);

        // then
        assertThat(polygon.getExteriorRing().toString(), is(equalTo("LINEARRING (34.877737 128.6917877, 34.8721037 128.6816597, 34.8530887 128.6845779, 34.853793 128.7118721, 34.877737 128.6917877)")));
        assertThat(polygon.getLength(), is(equalTo(0.08938231853062086d)));
        assertThat(polygon.getArea(), is(equalTo(4.383503918897806E-4d)));
        assertThat(polygon.contains(geometryFactory.createPoint(new Coordinate(34.877737, 128.6917877))), is(true));
    }
}
