package co.com.poli.dynamodb.helper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TemplateAdapterOperationsTest {

    /*@Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<ModelEntity> customerTable;

    private ModelEntity modelEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(dynamoDbEnhancedAsyncClient.table("table_name", TableSchema.fromBean(ModelEntity.class)))
                .thenReturn(customerTable);

        modelEntity = new ModelEntity();
        modelEntity.setId("id");
        modelEntity.setAtr1("atr1");
    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
        ModelEntity modelEntityUnderTest = new ModelEntity("id", "atr1");

        assertNotNull(modelEntityUnderTest.getId());
        assertNotNull(modelEntityUnderTest.getAtr1());
    }

    @Test
    void testSave() {
        when(customerTable.putItem(modelEntity)).thenReturn(CompletableFuture.runAsync(()->{}));
        when(mapper.map(modelEntity, ModelEntity.class)).thenReturn(modelEntity);

        ParkingAdapter parkingAdapter =
                new ParkingAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(parkingAdapter.save(modelEntity))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetById() {
        String id = "id";

        when(customerTable.getItem(
                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));
        when(mapper.map(modelEntity, Object.class)).thenReturn("value");

        ParkingAdapter parkingAdapter =
                new ParkingAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(parkingAdapter.getById("id"))
                .expectNext("value")
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(mapper.map(modelEntity, ModelEntity.class)).thenReturn(modelEntity);
        when(mapper.map(modelEntity, Object.class)).thenReturn("value");

        when(customerTable.deleteItem(modelEntity))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));

        ParkingAdapter parkingAdapter =
                new ParkingAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(parkingAdapter.delete(modelEntity))
                .expectNext("value")
                .verifyComplete();
    }*/
}