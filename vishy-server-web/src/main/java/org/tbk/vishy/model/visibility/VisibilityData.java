package org.tbk.vishy.model.visibility;

/**
 * Created by void on 26.12.14.
 */
public interface VisibilityData {

    long getTotalTime();

    long getHiddenTime();

    long getVisibleTime();

    long getFullyVisibleTime();

    long getRelativeTime();

    int getMaxPercentage();

    int getMinPercentage();
}
