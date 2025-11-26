package nl.novi.vinylshop.services;


import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.entities.StockEntity;
import nl.novi.vinylshop.mappers.entity.StockEntityMapper;
import nl.novi.vinylshop.models.StockModel;
import nl.novi.vinylshop.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockEntityMapper stockEntityMapper;

    public StockService(StockRepository stockRepository,
                        StockEntityMapper stockEntityMapper) {
        this.stockRepository = stockRepository;
        this.stockEntityMapper = stockEntityMapper;
    }

    @Transactional(readOnly = true)
    public List<StockModel> findAllStocks() {
        return stockRepository.findAll().stream()
                .map(stockEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StockModel findStockById(Long id) {
        StockEntity stockEntity = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock " + id + " not found"));
        return stockEntityMapper.fromEntity(stockEntity);
    }

    @Transactional
    public void deleteStock(Long albumId, Long id) {
        stockRepository.deleteByIdAndAlbumId(id, albumId);
    }

    @Transactional
    //nodig om fouten te voorkomen mocht door cascading meerdere entiteiten opgeslagen worden samen met deze entiteit.
    public StockModel createStock(Long albumId, StockModel stockModel) {
        StockEntity stockEntity = stockEntityMapper.toEntity(stockModel);
        stockEntity.setAlbum(new AlbumEntity(albumId));
        stockEntity = stockRepository.save(stockEntity);
        return stockEntityMapper.fromEntity(stockEntity);
    }

    @Transactional
    public StockModel updateStock(Long albumId, Long id, StockModel stockModel) {
        StockEntity stockEntity = getStockEntity(albumId, id);
        stockEntity.setCondition(stockModel.getCondition());
        stockEntity.setPrice(stockModel.getPrice());
        stockEntity = stockRepository.save(stockEntity);
        return stockEntityMapper.fromEntity(stockEntity);
    }

    private StockEntity getStockEntity(Long albumId, Long id) {
        StockEntity stockEntity = getByIdAndAlbumId(albumId, id)
                .orElseThrow(() -> new EntityNotFoundException("Stock " + id + " not found"));
        return stockEntity;
    }

    @Transactional
    public StockModel findStock(Long albumId, Long id) {
        StockEntity stockEntity = getByIdAndAlbumId(albumId, id)
                .orElseThrow(() -> new EntityNotFoundException("Stock " + id + " not found in album " + albumId));
        return stockEntityMapper.fromEntity(stockEntity);
    }

    private Optional<StockEntity> getByIdAndAlbumId(Long albumId, Long id) {
        return stockRepository.findByIdAndAlbumId(id, albumId);
    }

    public List<StockModel> findStock(Long albumId) {
        var stocks = stockRepository.findByAlbumId(albumId);
        return stockEntityMapper.fromEntities(stocks);
    }
}

