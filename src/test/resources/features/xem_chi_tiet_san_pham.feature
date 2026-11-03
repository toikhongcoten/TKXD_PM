@chitiet @shoestore
Feature: Chức năng 3 - Xem chi tiết sản phẩm và Phân loại trạng thái tồn kho biến thể
  In order to minh bạch thông tin sản phẩm và phân loại tình trạng tồn kho từng kích cỡ giúp khách hàng quyết định mua chính xác
  As a Người dùng hệ thống (Customer, Guest, Admin)
  I want to xem chi tiết sản phẩm cùng trạng thái tồn kho của các biến thể (AVAILABLE, LOW_STOCK, SOLD_OUT) theo quyền hạn

  Background:
    Given kho dữ liệu hệ thống đang lưu trữ các sản phẩm giày dép sau:
      | productId | name                       | category | brand    | basePrice | status   |
      | SP01      | Nike Air Zoom Pegasus 40   | Running  | Nike     | 3200000   | ACTIVE   |
      | SP02      | Adidas Ultraboost Light    | Running  | Adidas   | 4500000   | ACTIVE   |
      | SP03      | Biti's Hunter X Dune       | Sneaker  | Bitis    | 1100000   | ACTIVE   |
      | SP04      | Converse Chuck 70 Classic  | Sneaker  | Converse | 1800000   | INACTIVE |
      | SP05      | Puma Velocity Nitro 2      | Running  | Puma     | 2800000   | ACTIVE   |
    And các sản phẩm trên có danh sách biến thể (ProductVariant) chi tiết theo Size và Màu sắc như sau:
      | sku           | productId | size | color | price   | stock |
      | SP01-40-BLK   | SP01      | 40   | Black | 3200000 | 10    |
      | SP01-41-BLK   | SP01      | 41   | Black | 3200000 | 3     |
      | SP01-42-WHT   | SP01      | 42   | White | 3300000 | 0     |
      | SP02-41-WHT   | SP02      | 41   | White | 4500000 | 8     |
      | SP02-42-WHT   | SP02      | 42   | White | 4500000 | 2     |
      | SP03-39-GRN   | SP03      | 39   | Green | 1100000 | 15    |
      | SP03-41-GRN   | SP03      | 41   | Green | 1100000 | 0     |
      | SP04-41-BLK   | SP04      | 41   | Black | 1800000 | 5     |
      | SP05-40-ORG   | SP05      | 40   | Orange| 2800000 | 0     |

  Rule: Hệ thống phân loại trạng thái tồn kho của từng biến thể theo định mức: AVAILABLE (>5), LOW_STOCK (1-5), SOLD_OUT (0)

    Example: Trường hợp khách hàng xem chi tiết sản phẩm với trạng thái tồn kho của từng biến thể được phân loại chính xác
      Given tác nhân đang thực hiện yêu cầu có vai trò là "CUSTOMER"
      When tác nhân yêu cầu xem thông tin chi tiết của sản phẩm có mã "SP01"
      Then hệ thống trả về thông tin sản phẩm gốc "Nike Air Zoom Pegasus 40" thuộc danh mục "Running"
      And danh sách biến thể của sản phẩm "SP01" được phân loại trạng thái tồn kho chính xác như sau:
        | sku         | size | color | price   | stock | availabilityStatus |
        | SP01-40-BLK | 40   | Black | 3200000 | 10    | AVAILABLE          |
        | SP01-41-BLK | 41   | Black | 3200000 | 3     | LOW_STOCK          |
        | SP01-42-WHT | 42   | White | 3300000 | 0     | SOLD_OUT           |

  Rule: Quyền truy cập chi tiết sản phẩm được kiểm soát nghiêm ngặt dựa theo vai trò người dùng và trạng thái sản phẩm

    Example: Trường hợp quản trị viên xem thông tin chi tiết của sản phẩm đã ngừng kinh doanh
      Given tác nhân đang thực hiện yêu cầu có vai trò là "ADMIN"
      When tác nhân yêu cầu xem thông tin chi tiết của sản phẩm có mã "SP04"
      Then hệ thống trả về thông tin sản phẩm gốc "Converse Chuck 70 Classic" thuộc danh mục "Sneaker"
      And trạng thái kinh doanh của sản phẩm được ghi nhận là "INACTIVE"

    Example: Trường hợp khách vãng lai bị từ chối truy cập khi xem sản phẩm đã ngừng kinh doanh
      Given tác nhân đang thực hiện yêu cầu có vai trò là "GUEST"
      When tác nhân yêu cầu xem thông tin chi tiết của sản phẩm có mã "SP04"
      Then hệ thống từ chối cung cấp thông tin và trả về lỗi nghiệp vụ "Sản phẩm không tồn tại hoặc đã ngừng kinh doanh"

    Example: Trường hợp người dùng yêu cầu xem chi tiết một mã sản phẩm không tồn tại trong hệ thống
      Given tác nhân đang thực hiện yêu cầu có vai trò là "CUSTOMER"
      When tác nhân yêu cầu xem thông tin chi tiết của sản phẩm có mã "SP999"
      Then hệ thống từ chối cung cấp thông tin và trả về lỗi nghiệp vụ "Sản phẩm không tồn tại hoặc đã ngừng kinh doanh"
