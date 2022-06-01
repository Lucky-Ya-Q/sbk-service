import request from '@/utils/request'

// 查询西柏坡订单列表
export function listXbporder(query) {
  return request({
    url: '/service/xbporder/list',
    method: 'get',
    params: query
  })
}

// 查询西柏坡订单详细
export function getXbporder(orderId) {
  return request({
    url: '/service/xbporder/' + orderId,
    method: 'get'
  })
}

// 新增西柏坡订单
export function addXbporder(data) {
  return request({
    url: '/service/xbporder',
    method: 'post',
    data: data
  })
}

// 修改西柏坡订单
export function updateXbporder(data) {
  return request({
    url: '/service/xbporder',
    method: 'put',
    data: data
  })
}

// 删除西柏坡订单
export function delXbporder(orderId) {
  return request({
    url: '/service/xbporder/' + orderId,
    method: 'delete'
  })
}
