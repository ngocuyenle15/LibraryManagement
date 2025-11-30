package com.ptit.librarymanagement.service;

import com.ptit.librarymanagement.context.dao.DAOFactory;
import com.ptit.librarymanagement.dao.BookDAO;
import com.ptit.librarymanagement.dao.ShelfDAO;
import com.ptit.librarymanagement.dto.BookDTO;
import com.ptit.librarymanagement.dto.ShelfDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor
public class ShelfService {

    private final ShelfDAO shelfDAO;
    private final BookDAO bookDAO;

    public ShelfService(Connection connection) {
        this.shelfDAO = new ShelfDAO(connection);
        this.bookDAO = new BookDAO(connection);
    }

    public ShelfService(DAOFactory daoFactory) {
        this.shelfDAO = daoFactory.getShelfDAO();
        this.bookDAO = daoFactory.getBookDAO();
    }

    public List<BookDTO> getBookOfShelf (Integer id) {
        return bookDAO.getBookOfShelf(id);
    }

    public List<ShelfDTO> getAllShelves () {
        return shelfDAO.getAllShelves();
    }



    // --- 1. CREATE (Thêm mới kệ sách) ---
    public Integer createShelf(ShelfDTO shelfDTO) {
        // Có thể thêm validation (kiểm tra tên/vị trí không rỗng, v.v.)
        if (shelfDTO.getShelfName() == null || shelfDTO.getShelfName().trim().isEmpty()) {
            // Ném exception hoặc trả về giá trị lỗi phù hợp
            throw new IllegalArgumentException("Shelf name cannot be empty.");
        }
        return shelfDAO.insertShelf(shelfDTO);
    }

    // --- 2. READ (Lấy tất cả kệ sách) ---
    public List<ShelfDTO> findAllShelves() {
        return shelfDAO.getAllShelves();
    }

    // --- 3. READ (Lấy chi tiết kệ sách theo ID - Kèm sách) ---
    public Optional<ShelfDTO> findShelfDetailsById(int id) {
        // Sử dụng phương thức mới đã được bổ sung trong ShelfDAO
        return shelfDAO.getShelfByIdWithBooks(id);
    }

    // --- 4. READ (Lấy kệ sách theo ID - Không kèm sách) ---
    public ShelfDTO getShelfById(int id) {
        return shelfDAO.getShelfById(id).orElseThrow();
    }

    // --- 5. UPDATE (Cập nhật thông tin kệ sách) ---
    public boolean updateShelfDetails(ShelfDTO shelfDTO) {
        // Thêm logic nghiệp vụ: kiểm tra xem kệ có tồn tại không trước khi update
        if (shelfDTO.getId() == null || shelfDAO.getShelfById(shelfDTO.getId()).isEmpty()) {
            return false; // Kệ sách không tồn tại
        }
        return shelfDAO.updateShelf(shelfDTO);
    }

    // --- 6. DELETE (Xóa kệ sách) ---
    public boolean removeShelf(int id) {
        // Logic nghiệp vụ quan trọng: Trước khi xóa, kiểm tra xem kệ còn sách không.
        Optional<ShelfDTO> shelfDetails = findShelfDetailsById(id);

        if (shelfDetails.isPresent() && shelfDetails.get().getBooks() != null && !shelfDetails.get().getBooks().isEmpty()) {
            // Ví dụ: Ném lỗi nếu kệ vẫn còn sách
            throw new IllegalStateException("Cannot delete shelf ID " + id + ". There are still books on this shelf.");
            // Hoặc: Thực hiện logic chuyển sách sang kệ khác
        }

        // Nếu không có sách, tiến hành xóa
        return shelfDAO.deleteShelf(id);
    }
    public void deleteBookInShelf (BookDTO bookDTO) {
        shelfDAO.deleteBookInShelf(bookDTO);
    }



    public List<ShelfDTO> getShelfByIdOrNameOrLocation (ShelfDTO shelf) {
        return shelfDAO.getShelfByIdOrNameOrLocation(shelf);
    }
}
