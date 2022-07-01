import request from '@/utils/request'

// 查询邮政地址簿列表
export function listAddress(query) {
  return request({
    url: '/service/address/list',
    method: 'get',
    params: query
  })
}

// 查询邮政地址簿详细
export function getAddress(id) {
  return request({
    url: '/service/address/' + id,
    method: 'get'
  })
}

// 新增邮政地址簿
export function addAddress(data) {
  return request({
    url: '/service/address',
    method: 'post',
    data: data
  })
}

// 修改邮政地址簿
export function updateAddress(data) {
  return request({
    url: '/service/address',
    method: 'put',
    data: data
  })
}

// 删除邮政地址簿
export function delAddress(id) {
  return request({
    url: '/service/address/' + id,
    method: 'delete'
  })
}
