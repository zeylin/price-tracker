package com.zeylin.pricetracker.service;

import com.zeylin.pricetracker.dao.ArchiveDAO;
import com.zeylin.pricetracker.dto.PriceListDto;
import com.zeylin.pricetracker.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    private final ArchiveDAO archiveDAO;

    public ArchiveServiceImpl(ArchiveDAO archiveDAO) {
        this.archiveDAO = archiveDAO;
    }

    @Override
    public List<PriceListDto> list() {
        return archiveDAO.list();
    }

    @Override
    public void deletePermanently(Integer id) {
        int deleteCount = archiveDAO.deletePermanently(id);

        if (deleteCount == 0) {
            throw new NotFoundException("Price could not be found with id: " + id);
        }
    }

    @Override
    public void restore(Integer id) {
        int restoreCount = archiveDAO.restore(id);

        if (restoreCount == 0) {
            throw new NotFoundException("Price could not be found with id: " + id);
        }
    }

    @Override
    public void deleteAll() {
        archiveDAO.deleteAll();
    }
}
