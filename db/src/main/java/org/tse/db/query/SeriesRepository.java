package org.tse.db.query;

import org.springframework.stereotype.Repository;
import org.tse.db.types.Series;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

}
