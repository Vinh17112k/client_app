<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Java Techie Mail</title>
	<link href="./invoiceCss.css" rel="stylesheet" />
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body style="margin-top:20px;
background-color: #f7f7ff;">
<div class="container">
	<div class="card" style="position: relative;
    display: flex;
    flex-direction: column;
    min-width: 0;
    word-wrap: break-word;
    background-color: #fff;
    background-clip: border-box;
    border: 0px solid rgba(0, 0, 0, 0);
    border-radius: .25rem;
    margin-bottom: 1.5rem;
    box-shadow: 0 2px 6px 0 rgb(218 218 253 / 65%), 0 2px 6px 0 rgb(206 206 238 / 54%);">
		<div class="card-body">
			<div id="invoice" style="padding: 0px;">
				<div class="toolbar hidden-print">
					<div class="text-end">
						<button type="button" class="btn btn-dark"><i class="fa fa-print"></i> Print</button>
						<button type="button" class="btn btn-danger"><i class="fa fa-file-pdf-o"></i> Export as PDF</button>
					</div>
					<hr>
				</div>
				<div class="invoice overflow-auto" style="position: relative;
    background-color: #FFF;
    min-height: 680px;
    padding: 15px">
					<div style="min-width: 600px">
						<header style="padding: 10px 0;
    margin-bottom: 20px;
    border-bottom: 1px solid #0d6efd">
							<div class="row">
								<div class="col">
									<a href="javascript:;">
										<img src="assets/images/logo-icon.png" width="80" alt="">
									</a>
								</div>
								<div class="col company-details" style="text-align: right">
									<h2 class="name" style="margin-top: 0;
    margin-bottom: 0">
										<a target="_blank" href="javascript:;">
											${orderDetailDTO.customerDTO.username}
										</a>
									</h2>
									<div>${orderDetailDTO.addressDTO.wardName}</div>
									<div>${orderDetailDTO.customerDTO.phone}</div>
									<div>${orderDetailDTO.customerDTO.email}</div>
								</div>
							</div>
						</header>
						<main style="padding-bottom: 50px">
							<div class="row contacts">
								<div class="col invoice-to" style="text-align: left">
									<div class="text-gray-light">INVOICE TO:</div>
									<h2 class="to" style="margin-top: 0;
    margin-bottom: 0">${orderDetailDTO.customerDTO.username}</h2>
									<div class="address">${orderDetailDTO.addressDTO.provinceName} ${orderDetailDTO.addressDTO.districtName} ${orderDetailDTO.addressDTO.wardName}</div>
									<div class="email"><a href="mailto:john@example.com">${orderDetailDTO.customerDTO.email}</a>
									</div>
								</div>
								<div class="col invoice-details" style="text-align: right">
									<h1 class="invoice-id" style="margin-top: 0;
    color: #0d6efd">Hóa đơn thanh toán</h1>
									<div class="date">${orderDetailDTO.order.orderDate}</div>
								</div>
							</div>
							<div className="payment-method title-order">
								<div>
									<span>Phương thức thanh toán</span>
								</div>
								<div>
									<div>
										<span>OrderId: 1#</span>
										<span>Phương thức thanh toán: ${orderDetailDTO.payment.paymentMethod}</span>
									</div>
									<div>
										<span>Ngày đặt hàng: ${orderDetailDTO.order.orderDate}</span>
										<span>Phương thức giao hàng: ${orderDetailDTO.shipment.shippingMethod}</span>
									</div>
								</div>
							</div>
							<div className="delivery-address title-order">
								<div>
									<span>Địa chỉ giao hàng</span>
								</div>
								<div>
									<p>Tỉnh/Thành phố: ${orderDetailDTO.addressDTO.provinceName}</p>
									<p>Quận/Huyện: ${orderDetailDTO.addressDTO.districtName}</p>
									<p>Xã phường/Thị trấn: ${orderDetailDTO.addressDTO.wardName}</p>
									<p>Số nhà: số nhà ABC đường CDE</p>
								</div>
							</div>
							<table style="width: 100%;
    border-collapse: collapse;
    border-spacing: 0;
    margin-bottom: 20px">
								<thead>
								<tr>
									<th>#</th>
									<th class="text-left" style="white-space: nowrap;
    font-weight: 400;
    font-size: 16px; padding: 15px;
    background: #eee;
    border-bottom: 1px solid #fff">TÊN SẢN PHẨM</th>
									<th class="text-right" style="white-space: nowrap;
    font-weight: 400;
    font-size: 16px; padding: 15px;
    background: #eee;
    border-bottom: 1px solid #fff">Giá tiền</th>
								</tr>
								</thead>
								<tbody>
								<#list orderDetailDTO.productDTOS as product>
								<tr>
									<td class="no" style="color: #fff;
    font-size: 1.6em;
    background: #0d6efd; padding: 15px;
    background: #eee;
    border-bottom: 1px solid #fff">${product?index}</td>
									<td class="text-left" style="padding: 15px;
    background: #eee;
    border-bottom: 1px solid #fff">
										<h3 style="margin: 0;
    font-weight: 400;
    color: #0d6efd;
    font-size: 1.2em">
											<a target="_blank" href="javascript:;">
												${product.name}
											</a>
										</h3>
									<td class="unit" style="background: #ddd; text-align: right;
    font-size: 1.2em; padding: 15px;
    color: #0d6efd;
    border-bottom: 1px solid #fff">${product.price}</td>
								</tr>
								</#list>
								</tbody>
								<tfoot>
								<tr>
									<td colspan="2" style="background: 0 0;
    border-bottom: none;
    white-space: nowrap;
    text-align: right;
    padding: 10px 20px;
    font-size: 1.2em;
    border-top: 1px solid #aaa"></td>
									<td colspan="2" style="background: 0 0;
    border-bottom: none;
    white-space: nowrap;
    text-align: right;
    padding: 10px 20px;
    font-size: 1.2em;
    border-top: 1px solid #aaa">Giá trị đơn hàng</td>
									<td style="background: 0 0;
    border-bottom: none;
    white-space: nowrap;
    text-align: right;
    padding: 10px 20px;
    font-size: 1.2em;
    border-top: 1px solid #aaa">${orderDetailDTO.order.subTotal}</td>
								</tr>
								<tr>
									<td colspan="2" style="background: 0 0;
    border-bottom: none;
    white-space: nowrap;
    text-align: right;
    padding: 10px 20px;
    font-size: 1.2em;
    border-top: 1px solid #aaa"></td>
									<td colspan="2" style="background: 0 0;
    border-bottom: none;
    white-space: nowrap;
    text-align: right;
    padding: 10px 20px;
    font-size: 1.2em;
    border-top: 1px solid #aaa">Thuế giá trị</td>
									<td style="background: 0 0;
    border-bottom: none;
    white-space: nowrap;
    text-align: right;
    padding: 10px 20px;
    font-size: 1.2em;
    border-top: 1px solid #aaa" >${orderDetailDTO.order.taxRate}</td>
								</tr>
								<tr>
									<td colspan="2" style="background: 0 0;
    border-bottom: none;
    white-space: nowrap;
    text-align: right;
    padding: 10px 20px;
    font-size: 1.2em;
    border-top: 1px solid #aaa"></td>
									<td colspan="2" style="background: 0 0;
    border-bottom: none;
    white-space: nowrap;
    text-align: right;
    padding: 10px 20px;
    font-size: 1.2em;
    border-top: 1px solid #aaa">Tổng giá trị đơn hàng</td>
									<td style="background: 0 0;
    border-bottom: none;
    white-space: nowrap;
    text-align: right;
    padding: 10px 20px;
    font-size: 1.2em;
    border-top: 1px solid #aaa">${orderDetailDTO.order.grandTotal}</td>
								</tr>
								</tfoot>
							</table>
							<div className="order-history title-order">
								<div>
									<span>Lịch sử đặt hàng</span>
								</div>
								<Row>
									<Col span={12}>
									<span>Ngày đặt hàng: ${orderDetailDTO.order.orderDate}</span>
									</Col>
									<Col span={12}>
									<#if orderDetailDTO.order.status==0 >
										<span>Đang chờ</span>
									<#elseif orderDetailDTO.order.status==1 >
										<span>Chấp nhận</span>
									<#elseif orderDetailDTO.order.status==2 >
										<span>Đã Hủy</span>
									</#if>
									</Col>
								</Row>
							</div>
							<div class="thanks" style="margin-top: -100px;
    font-size: 2em;
    margin-bottom: 50px">Thank you!</div>
							<div class="notices" style="padding-left: 6px;
    border-left: 6px solid #0d6efd;
    background: #e7f2ff;
    padding: 10px; padding-left: 6px;
    border-left: 6px solid #0d6efd;
    background: #e7f2ff;
    padding: 10px;">
								<div>NOTICE:</div>
								<div class="notice" style="font-size: 1.2em">A finance charge of 1.5% will be made on unpaid balances after 30 days.</div>
							</div>
						</main>
						<footer style="width: 100%;
    text-align: center;
    color: #777;
    border-top: 1px solid #aaa;
    padding: 8px 0">Invoice was created on a computer and is valid without the signature and seal.</footer>
					</div>
					<!--DO NOT DELETE THIS div. IT is responsible for showing footer always at the bottom-->
					<div></div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
