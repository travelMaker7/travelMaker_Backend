package travelMaker.backend.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPraiseBadge is a Querydsl query type for PraiseBadge
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPraiseBadge extends BeanPath<PraiseBadge> {

    private static final long serialVersionUID = -2128738691L;

    public static final QPraiseBadge praiseBadge = new QPraiseBadge("praiseBadge");

    public final NumberPath<Integer> kingOfKindness = createNumber("kingOfKindness", Integer.class);

    public final NumberPath<Integer> photographer = createNumber("photographer", Integer.class);

    public final NumberPath<Integer> professionalGuide = createNumber("professionalGuide", Integer.class);

    public final NumberPath<Integer> timeIsGold = createNumber("timeIsGold", Integer.class);

    public QPraiseBadge(String variable) {
        super(PraiseBadge.class, forVariable(variable));
    }

    public QPraiseBadge(Path<? extends PraiseBadge> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPraiseBadge(PathMetadata metadata) {
        super(PraiseBadge.class, metadata);
    }

}

