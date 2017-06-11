import com.peopletechnologies.onlinestore.db.DBAccess
import com.peopletechnologies.onlinestore.rest.*
import com.peopletechnologies.onlinestore.services.*
import com.peopletechnologies.onlinestore.services.discount.BuyXofAgetYofAFree
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.gridfs.GridFsTemplate

/// Groovy Spring Context
Properties properties = new Properties();
File propFile = new File("/opt/bibleService/app.properties")
properties.load(new FileInputStream(propFile))

beans {
    xmlns([ctx: 'http://www.springframework.org/schema/context'])
    xmlns([mongo: "http://www.springframework.org/schema/data/mongo"])

    ctx.'component-scan'('base-package': "org.swordexplorer")

    ////////////////////////////////////////////////////////////////
    ///  Connector Objects for DB
    ////////////////////////////////////////////////////////////////

    mongo.mongo('id': "mongo", 'replica-set': "${properties['mongo.host']}")
    mongo.'db-factory'('id': "mongoDbFactory", 'dbname': "${properties['mongo.db.name']}", 'mongo-ref': 'mongo')
    mongo.'mapping-converter'("id": "mongoConverter", "db-factory-ref": "mongoDbFactory")

    mongoTemplate(MongoTemplate) { beanDefinition ->
        beanDefinition.constructorArgs = [ref("mongoDbFactory"), ref("mongoConverter")]
        writeConcern = "SAFE"
    }

    gridFsTemplate(GridFsTemplate) { beanDefinition ->
        beanDefinition.constructorArgs = [
                ref(mongoDbFactory),
                ref("mongoConverter")
        ]
    }

    dbAccess(DBAccess) { beanDefinition ->
        beanDefinition.constructorArgs = [ref("mongoTemplate")]
    }

    /////////////////////////////////////////////////////////////////
    //// Discount
    /////////////////////////////////////////////////////////////////
    buyXofAgetYofAFree(BuyXofAgetYofAFree) { beanDefinition ->
        beanDefinition.constructorArgs = []
    }

    discountProcessor(DiscountProcessor) { beanDefinition ->
        beanDefinition.constructorArgs = [
                [
                        buyXofAgetYofAFree: BuyXofAgetYofAFree
                ], ref('dbAccess')
        ]
    }

    /////////////////////////////////////////////////////////////////
    //// Services
    /////////////////////////////////////////////////////////////////

    userService(UserService) { beanDefinition ->
        beanDefinition.constructorArgs = [ref('mongoTemplate')]
    }

    mediaService(MediaService) { beanDefinition ->
        beanDefinition.constructorArgs = [
                ref('mongoTemplate'),
                ref('gridFsTemplate')
        ]
    }

    categoryService(CategoryService) { beanDefinition ->
        beanDefinition.constructorArgs = [ref('mongoTemplate')]
    }

    productService(ProductService) { beanDefinition ->
        beanDefinition.constructorArgs = [
                ref('mongoTemplate'),
                ref('gridFsTemplate'),
                ref('mediaService'),
                ref('categoryService')]
    }

    paymentProcessor(PaymentProcessor) { beanDefinition ->
        beanDefinition.constructorArgs = [ref('mongoTemplate'), ref('userService'), ref('productService')]
    }

    /////////////////////////////////////////////////////////////////
    //// REST Resources
    /////////////////////////////////////////////////////////////////
    cartResource(CartResource) { beanDefinition ->
        beanDefinition.constructorArgs = [ref('userService')]
    }

    mediaResource(MediaResource) { beanDefinition ->
        beanDefinition.constructorArgs = [ref('mediaService')]
    }

    categoryResource(CategoryResource) { beanDefinition ->
        beanDefinition.constructorArgs = [ref('categoryService')]
    }

    paymentResource(PaymentResource) { beanDefinition ->
        beanDefinition.constructorArgs = [ref('paymentProcessor')]
    }

    productResource(ProductResource) { beanDefinition ->
        beanDefinition.constructorArgs = [ref('productService')]
    }

    /////////////////////////////////////////////////////////////////
    //// REST Server Instance
    /////////////////////////////////////////////////////////////////
    restService(RestService) { beanDefinition ->
        beanDefinition.constructorArgs = [
                ref('productResource'),
                ref('cartResource'),
                ref('paymentResource'),
                ref('categoryResource'),
                ref('mediaResource'),
        ]
    }

    /////////////////////////////////////////////////////////////////
    //// Data Loader
    /////////////////////////////////////////////////////////////////
    dataLoader(DataLoader) {
        beanDefinition ->
            beanDefinition.constructorArgs = [
                    ref('productService'),
                    ref('categoryService'),
                    ref('userService'),
                    ref('mediaService')]
    }

}
