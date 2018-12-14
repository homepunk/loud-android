package homepunk.github.com.domain.repository;

import java.util.concurrent.Future;

import homepunk.github.com.domain.model.DiscogsArtist;
import io.reactivex.Single;
import io.reactivex.internal.subscribers.FutureSubscriber;

public interface DiscogsRepository {
    Single<DiscogsArtist> getArtist(String name);
}
