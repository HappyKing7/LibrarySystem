const Mock = require('mockjs')
const Random = Mock.Random

let Result = {
    code: 200,
    msg: '操作成功',
    data:null
}

Mock.mock('/codeImg','get',()=>{
    let codeData=Random.string('upper', 1) + Random.string('number', 2)+ Random.string('lower', 2)
    Result.data = {
        token: Random.string(32),
        codeImg: Random.dataImage('100x31',codeData),
        codeData: codeData
    }

     return Result
})

Mock.mock('/login','post',(config)=>{
/*    Result.code = 400
    Result.msg = '验证码错误'*/
    console.log(config)
    Result.code=200,
    Result.msg='操作成功'
    return Result
})

Mock.mock('/user/userInfo','get',()=>{
    Result.data = {
        userID:"1",
        userName:"test",
        avatar:"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fci.xiaohongshu.com%2Fa8294ab4-4964-35ca-9f74-5c736f1e48c4%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fci.xiaohongshu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666076615&t=f085dc95347cd378d44dacc0d4bc5018",
        userType:"0"
    }
    return Result
})

Mock.mock('/logout','get',()=>{
    return Result
})

Mock.mock('/list','post',(config)=>{
    console.log(config)
    Result.data = {
        current: 1,
        size:1,
        total:2,
        tableData: [
            {
                BOOK_ID:"9787020070527",
                BOOK_NAME:"三国演义",
                BOOK_TYPE_ID:"文学",
                AUTHOR_ID:"罗贯中",
                BORROWED_NUMBER:"1",
                REMAINING_NUMBER:"9",
                BOOK_STATUS:"0"
            },
            {
                BOOK_ID:"9787020070527",
                BOOK_NAME:"三国演义",
                BOOK_TYPE_ID:"文学",
                AUTHOR_ID:"罗贯中",
                BORROWED_NUMBER:"1",
                REMAINING_NUMBER:"9",
                BOOK_STATUS:"0"
            }
        ]
    }
    return Result
})

