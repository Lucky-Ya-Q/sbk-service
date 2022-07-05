import request from '@/utils/request'

// 查询邮政订单列表
export function listEmsorder(query) {
  return request({
    url: '/service/emsorder/list',
    method: 'get',
    params: query
  })
}

// 查询邮政订单详细
export function getEmsorder(id) {
  return request({
    url: '/service/emsorder/' + id,
    method: 'get'
  })
}

// 新增邮政订单
export function addEmsorder(data) {
  return request({
    url: '/service/emsorder',
    method: 'post',
    data: data
  })
}

// 修改邮政订单
export function updateEmsorder(data) {
  return request({
    url: '/service/emsorder',
    method: 'put',
    data: data
  })
}

// 删除邮政订单
export function delEmsorder(id) {
  return request({
    url: '/service/emsorder/' + id,
    method: 'delete'
  })
}
