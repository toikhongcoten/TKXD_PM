@loc @shoestore
Feature: Chức năng 2 - Lọc sản phẩm đa tiêu chí theo Danh mục, Cỡ giày và Khoảng giá
  In order to giúp khách hàng dễ dàng tìm kiếm đôi giày vừa vặn cỡ chân còn hàng và phù hợp ngân sách mua sắm
  As a Khách hàng mua sắm (Customer, Guest)
  I want to lọc sản phẩm theo đa tiêu chí (Danh mục, Cỡ giày còn tồn kho, Khoảng giá) và kiểm tra chặt chẽ tính hợp lệ của tham số lọc

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

  Rule: Kết quả lọc đa tiêu chí phải thỏa mãn đồng thời danh mục, khoảng giá và cỡ giày thực sự còn hàng trong kho

    Example: Trường hợp khách hàng lọc thành công theo danh mục, cỡ giày còn hàng và khoảng giá hợp lệ
      Given tác nhân đang thực hiện yêu cầu có vai trò là "CUSTOMER"
      When tác nhân lọc sản phẩm theo danh mục "Running", cỡ giày 41, và khoảng giá từ 3000000 đến 4000000
      Then hệ thống trả về danh sách gồm 1 sản phẩm khớp yêu cầu:
        | productId | name                     | status |
        | SP01      | Nike Air Zoom Pegasus 40 | ACTIVE |

    Example: Trường hợp cỡ giày yêu cầu đã hết số lượng tồn kho (tồn kho bằng 0)
      Given tác nhân đang thực hiện yêu cầu có vai trò là "GUEST"
      When tác nhân lọc sản phẩm theo danh mục "Sneaker", cỡ giày 41, và khoảng giá từ 500000 đến 5000000
      Then hệ thống trả về danh sách sản phẩm rỗng
      And hệ thống ghi nhận thông điệp nghiệp vụ "Không tìm thấy sản phẩm nào phù hợp với tiêu chí tìm kiếm"

  Rule: Các yêu cầu lọc vi phạm điều kiện nghiệp vụ phải bị từ chối với thông báo lỗi thích hợp

    Scenario Outline: Kiểm tra tính hợp lệ của khoảng giá (giá tối thiểu không được lớn hơn giá tối đa và không được âm)
      Given tác nhân đang thực hiện yêu cầu có vai trò là "CUSTOMER"
      When tác nhân lọc sản phẩm với mức giá tối thiểu là <minPrice> và mức giá tối đa là <maxPrice>
      Then hệ thống từ chối thực thi tìm kiếm và trả về lỗi nghiệp vụ "<errorMessage>"

      Examples: Bảng dữ liệu kiểm thử quy tắc khoảng giá
        | minPrice | maxPrice | errorMessage                                              | Notes / Kịch bản kiểm thử                       |
        | 5000000  | 2000000  | Khoảng giá không hợp lệ: Giá tối thiểu lớn hơn giá tối đa | Lỗi giá tối thiểu lớn hơn giá tối đa            |
        | -100000  | 3000000  | Số tiền không được phép mang giá trị âm                   | Lỗi mức giá tối thiểu là số âm                  |

    Scenario Outline: Kiểm tra tính hợp lệ của cỡ giày theo chuẩn kích thước cho phép từ 20 đến 55
      Given tác nhân đang thực hiện yêu cầu có vai trò là "GUEST"
      When tác nhân lọc sản phẩm với cỡ giày yêu cầu là <invalidSize>
      Then hệ thống từ chối thực thi tìm kiếm và trả về lỗi nghiệp vụ "<errorMessage>"

      Examples: Bảng dữ liệu kiểm thử quy tắc biên cỡ giày
        | invalidSize | errorMessage                                                | Notes / Kịch bản kiểm thử                       |
        | 15          | Cỡ giày không hợp lệ: Size giày phải nằm trong khoảng 20-55 | Cỡ giày nhỏ hơn ngưỡng tối thiểu 20             |
        | 60          | Cỡ giày không hợp lệ: Size giày phải nằm trong khoảng 20-55 | Cỡ giày vượt quá ngưỡng tối đa 55               |
