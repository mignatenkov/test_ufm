package com.andersen.test_ufm.akka.actors;

import com.andersen.test_ufm.repository.ClientDBRepository;
import com.andersen.test_ufm.repository.FileRepository;
import com.andersen.test_ufm.service.IProcessService;
import com.mongodb.DBObject;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ActorUtilTest {

    private static final String TEST_DIR = "src" + File.separator + "test" + File.separator + "resources";
    private static final String TEST_INPUT_DIR = TEST_DIR + File.separator + "input";
    private static final String TEST_PROCESSED_DIR = TEST_DIR + File.separator + "processed";
    private static final String TEST_OUTPUT_DIR = TEST_DIR + File.separator + "output";

    @Mock
    private IProcessService processService;

    @Mock
    FileRepository fileRepository;

    @Mock
    private ClientDBRepository clientDBRepository;

    @InjectMocks
    private ActorUtil util = new ActorUtil();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldProcessSuccessfully(){
        when(fileRepository.getInputFilesDir()).thenReturn(TEST_INPUT_DIR);
        when(fileRepository.getProcessedFilesDir()).thenReturn(TEST_PROCESSED_DIR);
        when(fileRepository.getOutputFilesDir()).thenReturn(TEST_OUTPUT_DIR);
        JSONObject jsonObject = mock(JSONObject.class);
        DBObject dbObject = mock(DBObject.class);
        File file = new File(TEST_INPUT_DIR + File.separator + "test.json");

        when(processService.process(Mockito.any(JSONObject.class))).thenReturn(jsonObject);
        when(clientDBRepository.createClient(jsonObject)).thenReturn(dbObject);

        util.process(file);

        verify(processService).process(Mockito.any(JSONObject.class));
        ArgumentCaptor<File> destFileCaptor = ArgumentCaptor.forClass(File.class);
        verify(fileRepository).moveFile(eq(file), destFileCaptor.capture());
        Assert.assertEquals(TEST_PROCESSED_DIR + File.separator + "test.json", destFileCaptor.getValue().getPath());
        verify(fileRepository).createOutputFile(file.getName(), jsonObject);
        verify(clientDBRepository).createClient(jsonObject);
    }

    @Test
    public void shouldNotProcessNonJSONFile(){
        when(fileRepository.getInputFilesDir()).thenReturn(TEST_INPUT_DIR);
        when(fileRepository.getProcessedFilesDir()).thenReturn(TEST_PROCESSED_DIR);
        when(fileRepository.getOutputFilesDir()).thenReturn(TEST_OUTPUT_DIR);
        DBObject dbObject = mock(DBObject.class);
        File file = new File(TEST_INPUT_DIR + File.separator + "test.non_json");
        JSONObject emptyJson = new JSONObject();

        when(clientDBRepository.createClient(eq(emptyJson))).thenReturn(dbObject);

        util.process(file);

        ArgumentCaptor<File> destFileCaptor = ArgumentCaptor.forClass(File.class);
        verify(fileRepository).moveFile(eq(file), destFileCaptor.capture());
        Assert.assertEquals(TEST_PROCESSED_DIR + File.separator + "test.non_json", destFileCaptor.getValue().getPath());
        verify(fileRepository).createOutputFile(eq(file.getName()), eq(emptyJson));
        verify(clientDBRepository).createClient(eq(emptyJson));
    }

}