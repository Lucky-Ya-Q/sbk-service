import request from '@/utils/request'

// 查询图书馆读者证列表
export function listReader(query) {
  return request({
    url: '/service/reader/list',
    method: 'get',
    params: query
  })
}

// 查询图书馆读者证详细
export function getReader(id) {
  return request({
    url: '/service/reader/' + id,
    method: 'get'
  })
}

// 新增图书馆读者证
export function addReader(data) {
  return request({
    url: '/service/reader',
    method: 'post',
    data: data
  })
}

// 修改图书馆读者证
export function updateReader(data) {
  return request({
    url: '/service/reader',
    method: 'put',
    data: data
  })
}

// 删除图书馆读者证
export function delReader(id) {
  return request({
    url: '/service/reader/' + id,
    method: 'delete'
  })
}
