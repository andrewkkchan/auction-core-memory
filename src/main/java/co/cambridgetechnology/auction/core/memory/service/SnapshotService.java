package co.cambridgetechnology.auction.core.memory.service;

import co.cambridgetechnology.auction.core.memory.model.request.impl.SnapshotRequest;

/**
 * Serializable isolated transactional service to mutate {@link Account}
 */
public interface SnapshotService {
    /**
     * snapshot by capturing {@link JournalEntry} for required {@link Account}
     * and calculated {@link Account} exactly and precisely up to the snapshot epoch
     * @param snapshotRequest requires  which {@link Account} to snapshot, null means all accounts are required
     * @return timestamp of the snapshot epoch which the snapshot are accurate as at.
     */
    long snapshot(SnapshotRequest snapshotRequest);
}
