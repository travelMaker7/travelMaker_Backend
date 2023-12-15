package travelMaker.backend.mypage.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -1440577102L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final NumberPath<Integer> kingOfKindness = createNumber("kingOfKindness", Integer.class);

    public final NumberPath<Double> mannerScore = createNumber("mannerScore", Double.class);

    public final NumberPath<Integer> photographer = createNumber("photographer", Integer.class);

    public final NumberPath<Integer> professionalGuide = createNumber("professionalGuide", Integer.class);

    public final travelMaker.backend.user.model.QUser reviewer;

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final travelMaker.backend.user.model.QUser reviewTarget;

    public final NumberPath<Integer> timeIsGold = createNumber("timeIsGold", Integer.class);

    public final NumberPath<Long> tripPlanId = createNumber("tripPlanId", Long.class);

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reviewer = inits.isInitialized("reviewer") ? new travelMaker.backend.user.model.QUser(forProperty("reviewer")) : null;
        this.reviewTarget = inits.isInitialized("reviewTarget") ? new travelMaker.backend.user.model.QUser(forProperty("reviewTarget")) : null;
    }

}

