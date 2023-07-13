import groovy.json.JsonBuilder

/*
заливает новые объекты
в примере заливаем только 1 аттрибут в объект Name
есть возможность сразу множество добавлять
*/


def unitNames = """
"Бизнес Юнит name",
"odasddsd",
"abcder",
"Цифровое образование"
""".split(',\n').collect { it.replaceAll(/"/, '').trim() }

//  адрес реста куда обращаться
def url = "https://jirajirajira/rest/insight/1.0/object/create"
//  собирает хедеры
Map<String, String> headers = new HashMap<String, String>()
headers["Content-Type"] = "application/json"
//  здесь ваш токен джиры, без авторизации никак
headers["Authorization"] = "Bearer sasdasdasdasd" 

//
//  обработка
//
unitNames.each { name ->
  def jsonBody = new JsonBuilder()
jsonBody {
    attributes([
        [
            objectTypeAttributeId: 717, //	здесь айдишка аттрибута. В моем случае Name, в моем случае 717
            objectAttributeValues: [
                [
                    value: name
                ]
            ]
        ]
    ])
    objectTypeId(103)	//	здесь айдишка объект схемы, куда заливаются объекты
}
  def response = http.POST(url, headers, jsonBody.toPrettyString())
  
}
