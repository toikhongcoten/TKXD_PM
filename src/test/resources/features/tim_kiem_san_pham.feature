@timkiem @shoestore
Feature: Chức năng 1 - Tìm kiếm sản phẩm theo từ khóa và Phân quyền Actor
  In order to giúp khách hàng nhanh chóng tìm thấy sản phẩm giày dép cần mua và tối ưu doanh thu bán hàng
  As a Người dùng hệ thống (Guest, Customer, Admin)
  I want to tìm kiếm sản phẩm theo từ khóa với phạm vi dữ liệu được kiểm soát theo đúng quyền hạn nghiệp vụ

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

  Rule: Khách vãng lai và khách hàng chỉ được tìm kiếm các sản phẩm đang mở bán (ACTIVE)

    Example: Trường hợp khách vãng lai tìm kiếm sản phẩm đang mở bán theo từ khóa thương hiệu
      Given tác nhân đang thực hiện yêu cầu có vai trò là "GUEST"
      When tác nhân thực hiện tìm kiếm sản phẩm với từ khóa "Nike"
      Then hệ thống trả về danh sách gồm 1 sản phẩm khớp yêu cầu:
        | productId | name                     | status |
        | SP01      | Nike Air Zoom Pegasus 40 | ACTIVE |

    Example: Trường hợp khách hàng tìm kiếm sản phẩm đã ngừng kinh doanh và hệ thống tự động ẩn đi
      Given tác nhân đang thực hiện yêu cầu có vai trò là "CUSTOMER"
      When tác nhân thực hiện tìm kiếm sản phẩm với từ khóa "Chuck 70"
      Then hệ thống trả về danh sách sản phẩm rỗng
      And hệ thống ghi nhận thông điệp nghiệp vụ "Không tìm thấy sản phẩm nào phù hợp với tiêu chí tìm kiếm"

  Rule: Quản trị viên (Admin) có thẩm quyền tra cứu toàn bộ sản phẩm bao gồm cả sản phẩm ngừng kinh doanh

    Example: Trường hợp quản trị viên tra cứu từ khóa có kèm tùy chọn hiển thị sản phẩm ngừng kinh doanh
      Given tác nhân đang thực hiện yêu cầu có vai trò là "ADMIN"
      When tác nhân thực hiện tra cứu sản phẩm với từ khóa "Chuck 70" cùng tùy chọn bao gồm sản phẩm ngừng kinh doanh
      Then hệ thống trả về danh sách gồm 1 sản phẩm khớp yêu cầu:
        | productId | name                      | status   |
        | SP04      | Converse Chuck 70 Classic | INACTIVE |

  Rule: Từ khóa không tồn tại trong hệ thống phải trả về kết quả rỗng và thông điệp hướng dẫn

    Example: Trường hợp từ khóa tìm kiếm không khớp với bất kỳ sản phẩm nào trong kho dữ liệu
      Given tác nhân đang thực hiện yêu cầu có vai trò là "GUEST"
      When tác nhân thực hiện tìm kiếm sản phẩm với từ khóa "Balenciaga"
      Then hệ thống trả về danh sách sản phẩm rỗng
      And hệ thống ghi nhận thông điệp nghiệp vụ "Không tìm thấy sản phẩm nào phù hợp với tiêu chí tìm kiếm"
