package nl.novi.vinylshop.dtos.album;


import nl.novi.vinylshop.dtos.stock.StockResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class AlbumExtendedResponseDTO extends AlbumResponseDTO  {
    public List<StockResponseDTO> getStock() {
        return stock;
    }

    public void setStock(List<StockResponseDTO> stock) {
        this.stock = stock;
    }

    private List<StockResponseDTO> stock = new ArrayList<>();
}
