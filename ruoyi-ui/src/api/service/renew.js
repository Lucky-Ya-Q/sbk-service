import request from '@/utils/request'

// 查询续借记录列表
export function listRenew(query) {
  return request({
    url: '/service/renew/list',
    method: 'get',
    params: query
  })
}

// 查询续借记录详细
export function getRenew(id) {
  return request({
    url: '/service/renew/' + id,
    method: 'get'
  })
}

// 新增续借记录
export function addRenew(data) {
  return request({
    url: '/service/renew',
    method: 'post',
    data: data
  })
}

// 修改续借记录
export function updateRenew(data) {
  return request({
    url: '/service/renew',
    method: 'put',
    data: data
  })
}

// 删除续借记录
export function delRenew(id) {
  return request({
    url: '/service/renew/' + id,
    method: 'delete'
  })
}
