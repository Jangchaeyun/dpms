import {
  CREATE_COMMENT_REQUEST,
  DELETE_COMMENT_REQUEST,
  FETCH_COMMENTS_REQUEST,
  CREATE_COMMENT_SUCCESS,
  DELETE_COMMENT_SUCCESS,
  FETCH_COMMENTS_SUCCESS,
  CREATE_COMMENT_FAILURE,
  DELETE_COMMENT_FAILURE,
  FETCH_COMMENTS_FAILURE,
} from "./ActionTypes";

const initialState = {
  messages: [],
  loading: false,
  error: null,
  chat: null,
};

const commentReducer = (state = initialState, action) => {
  switch (action.type) {
    case CREATE_COMMENT_REQUEST:
    case DELETE_COMMENT_REQUEST:
    case FETCH_COMMENTS_REQUEST:
      return {
        ...state,
        loading: true,
        error: null,
      };
    case CREATE_COMMENT_SUCCESS:
      return {
        ...state,
        loading: false,
        comments: [...state.comments, action.comment],
      };
    case DELETE_COMMENT_SUCCESS:
      return {
        ...state,
        loading: false,
        comments: state.comments.filter(
          (comment) => comment.id !== action.commentId
        ),
      };
    case FETCH_COMMENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        comments: action.comments,
      };
    case CREATE_COMMENT_FAILURE:
    case DELETE_COMMENT_FAILURE:
    case FETCH_COMMENTS_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.error,
      };

    default:
      return state;
  }
};

export default commentReducer;
