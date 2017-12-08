package com.zhang.box.loading;


import com.zhang.box.loading.anim.SnakeCircleBuilder;
import com.zhang.box.loading.text.TextBuilder;

/**
 *
 */
public enum Z_TYPE
{
//    CIRCLE(CircleBuilder.class),
//    CIRCLE_CLOCK(ClockBuilder.class),
//    STAR_LOADING(StarBuilder.class),
//    LEAF_ROTATE(LeafBuilder.class),
//    DOUBLE_CIRCLE(DoubleCircleBuilder.class),
//    PAC_MAN(PacManBuilder.class),
//    ELASTIC_BALL(ElasticBallBuilder.class),
//    INFECTION_BALL(InfectionBallBuilder.class),
//    INTERTWINE(IntertwineBuilder.class),
    TEXT(TextBuilder.class),
//    SEARCH_PATH(SearchPathBuilder.class),
//    ROTATE_CIRCLE(RotateCircleBuilder.class),
//    SINGLE_CIRCLE(SingleCircleBuilder.class),
    SNAKE_CIRCLE(SnakeCircleBuilder.class)
    ;

    private final Class<?> mBuilderClass;

    Z_TYPE(Class<?> builderClass)
    {
        this.mBuilderClass = builderClass;
    }

    <T extends ZLoadingBuilder>T newInstance(){
        try
        {
            return (T) mBuilderClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
