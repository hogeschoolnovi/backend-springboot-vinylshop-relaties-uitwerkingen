package nl.novi.vinylshop.controllers;

import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.stock.StockRequestDTO;
import nl.novi.vinylshop.dtos.stock.StockResponseDTO;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.mappers.dto.StockDTOMapper;
import nl.novi.vinylshop.models.StockModel;
import nl.novi.vinylshop.services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums/{albumId}/stock")
public class StockController {

    private final StockService stockService;
    private final StockDTOMapper stockMapper;
    private final UrlHelper urlHelper;


    public StockController(StockService stockService, StockDTOMapper stockMapper,UrlHelper urlHelper) {
        this.stockService = stockService;
        this.stockMapper = stockMapper;
        this.urlHelper = urlHelper;
    }

    @PostMapping()
    public ResponseEntity<StockResponseDTO> updateAlbumData(@PathVariable Long albumId, @RequestBody @Valid StockRequestDTO stockDTO) {
        StockModel coverModel = stockMapper.mapToModel(stockDTO);
        coverModel = stockService.createStock(albumId, coverModel);
        StockResponseDTO responseDTO = stockMapper.mapToDto(coverModel);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId( responseDTO.getId())).body(responseDTO);
    }

    @GetMapping()
    public ResponseEntity<List<StockResponseDTO>> getStock(@PathVariable Long albumId) {
        var coverModels = stockService.findStock(albumId);
        var responseDTOs = stockMapper.mapToDtos(coverModels);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDTO> getStock(@PathVariable Long albumId, @PathVariable Long id) {
        StockModel coverModel = stockService.findStock(albumId, id);
        StockResponseDTO responseDTO = stockMapper.mapToDto(coverModel);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockResponseDTO> updateStock(@PathVariable Long albumId, @PathVariable Long id, @RequestBody  @Valid StockRequestDTO coverDTO) {
        StockModel coverModel = stockMapper.mapToModel(coverDTO);
        coverModel = stockService.updateStock(albumId, id, coverModel);
        StockResponseDTO responseDTO = stockMapper.mapToDto(coverModel);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long albumId, @PathVariable Long id) {
        stockService.deleteStock(albumId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
