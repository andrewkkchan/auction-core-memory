package co.cambridgetechnology.auction.core.memory.service.impl;

import co.cambridgetechnology.auction.core.memory.model.request.impl.SnapshotRequest;
import co.cambridgetechnology.auction.core.memory.service.SnapshotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SnapshotServiceImpl implements SnapshotService {

    public SnapshotServiceImpl() {

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long snapshot(SnapshotRequest snapshotRequest) {
        return new Date().getTime();
    }
}
